package com.tomtresansky.gradle.plugin.configurationreport.task

import com.tomtresansky.gradle.plugin.configurationreport.ConfigurationReportPluginExtension
import com.tomtresansky.gradle.plugin.configurationreport.ReportFormat
import com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz.GraphVizConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.engine.text.TextConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.graph.IConfigurationReportGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

/**
 * Task to output a report containing a graph of projects' configurations' information.
 *
 * All task classes must be defined as open.  If not set correctly, Gradle will try to proxy your class and fail the build.
 */
open class ConfigurationReportTask : DefaultTask() {
    companion object {
        const val TASK_NAME = "configurationReport"
        const val TASK_GROUP = "reporting"
        const val TASK_DESCRIPTION = "Generates an HTML report about the project's configurations and their relationships."
    }

    @TaskAction
    fun generate() {
        val extension: ConfigurationReportPluginExtension = project.extensions.findByName(ConfigurationReportPluginExtension.NAME) as ConfigurationReportPluginExtension

        var generator = when (extension.format) {
            ReportFormat.GRAPH_VIZ -> GraphVizConfigurationReportGenerator(extension.baseDir)
            ReportFormat.TEXT -> TextConfigurationReportGenerator()
        }

        generator.generate(extension.graph ?: throw IllegalStateException("Graph not available!"))
    }
}