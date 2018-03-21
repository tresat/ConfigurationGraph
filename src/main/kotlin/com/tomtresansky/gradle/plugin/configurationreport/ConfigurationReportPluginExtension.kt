package com.tomtresansky.gradle.plugin.configurationreport

import com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz.GraphVizConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.engine.text.TextConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationGraph
import org.gradle.api.Project
import org.gradle.api.reporting.ReportingExtension
import java.io.File

open class ConfigurationReportPluginExtension(project: Project): ReportingExtension(project) {
    companion object {
        const val NAME = "configurationGraph"
        const val CONFIGURATION_GRAPH_REPORTS_DIR_NAME = "configurationsGraph"
        val DEFAULT_FORMAT = ReportFormat.GRAPH_VIZ
    }

    var outputDir = File("$baseDir/$CONFIGURATION_GRAPH_REPORTS_DIR_NAME")
    var format = DEFAULT_FORMAT
}