package com.tomtresansky.gradle.plugin.configurationreport.internal

import org.gradle.api.Project

class ConfigurationDataExtractor {
    fun extractConfigurationData(project: Project): List<ConfigurationNode> {
        val result: List<ConfigurationNode> = arrayListOf()

        with(project) {
            configurations.forEach { c ->
                TODO()
            }
        }

        return result.sortedBy { n -> n.config.name }
    }
}
