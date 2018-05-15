package com.tomtresansky.gradle.plugin.configurationreport.graph

import com.google.common.annotations.VisibleForTesting
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class ConfigurationDataExtractor {
    fun extractConfigurationData(project: Project): ConfigurationGraph {
        val configs = project.configurations.toCollection(mutableListOf())
        val nodes = extractConfigurationData(configs)

        return ConfigurationGraph(project.name, nodes)
    }

    @VisibleForTesting
    internal fun extractConfigurationData(configurations: List<Configuration>): List<ConfigurationNode> {
        val extensionMap: MutableMap<Configuration, MutableList<Configuration>> = configurations.associateByTo(mutableMapOf<Configuration, MutableList<Configuration>>(),
                                                                                                               { it },
                                                                                                               { mutableListOf<Configuration>() })
        // Build parent -> children extensions mapping
        configurations.forEach { child ->
            child.extendsFrom.forEach { parent ->
                val extensions = extensionMap[parent] as MutableList<Configuration> // All should have been added
                extensions.add(child)
            }
        }

        // Keep a map of each ConfigurationNode we've already built
        val results: MutableMap<Configuration, ConfigurationNode> = mutableMapOf()
        while (extensionMap.isNotEmpty()) {
            // Check for configurations with either known children, or children already present in the map
            for ((parent, children) in extensionMap) {
                if (children.isEmpty()) {
                    results.put(parent, ConfigurationNode(parent.name, parent.isTransitive))
                } else if (results.keys.containsAll(children)) {
                    val sortedChildren = children.map { child -> results[child] as ConfigurationNode }
                                                 .sortedBy { child -> child.configName }
                    results.put(parent, ConfigurationNode(parent.name, parent.isTransitive, sortedChildren))
                }
            }

            // Remove everything we just added
            results.map { it.key }.forEach { extensionMap.remove(it) }
        }

        // Return the sorted list of config nodes
        return results.values.sortedBy { it.configName }
    }
}
