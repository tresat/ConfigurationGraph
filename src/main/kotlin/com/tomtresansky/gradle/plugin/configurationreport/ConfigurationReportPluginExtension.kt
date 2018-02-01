package com.tomtresansky.gradle.plugin.configurationreport

import org.gradle.api.Project
import org.gradle.api.reporting.ReportingExtension

open class ConfigurationReportPluginExtension(project: Project): ReportingExtension(project) {
    companion object {
        const val DEFAULT_OUTPUT_FILE_NAME = "configuration_graph.txt"
        const val CONFIGURATION_GRAPH_REPORTS_DIR_NAME = "configurationsGraph"
    }

    var outputFileName = DEFAULT_OUTPUT_FILE_NAME
}