package com.tomtresansky.gradle.plugin.configurationgraph

import org.gradle.api.Project
import org.gradle.api.reporting.ReportingExtension

open class ConfigurationGraphPluginExtension(project: Project): ReportingExtension(project) {
    companion object {
        const val DEFAULT_OUTPUT_FILE_NAME = "configuration_graph.txt"
    }

    var outputFileName = DEFAULT_OUTPUT_FILE_NAME
}