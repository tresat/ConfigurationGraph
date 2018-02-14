package com.tomtresansky.gradle.plugin.configurationreport.internal

import com.google.common.annotations.VisibleForTesting
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class ConfigurationDataExtractor {
    fun extractConfigurationData(project: Project): List<ConfigurationNode> {
        val configs: MutableList<Configuration> = project.configurations.toCollection(mutableListOf())
        return extractConfigurationData(configs)
    }

    @VisibleForTesting
    internal fun extractConfigurationData(configurations: List<Configuration>): List<ConfigurationNode> {
        val result: MutableList<ConfigurationNode> = arrayListOf()

        configurations.forEach { c ->
            val node = ConfigurationNode(c, mutableListOf())

            result.add(node)
        }

        return result.sortedBy { n -> n.config.name }
    }
}
