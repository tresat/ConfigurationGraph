package com.tomtresansky.gradle.plugin.configurationreport.task

import com.google.common.annotations.VisibleForTesting
import com.tomtresansky.gradle.plugin.configurationreport.ConfigurationReportPluginExtension
import com.tomtresansky.gradle.plugin.configurationreport.ReportFormat
import com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz.GraphVizConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.engine.text.TextConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationGraph
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

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

    @InputFile
    lateinit var graphFile: File

    @TaskAction
    fun generate() {
        val extension: ConfigurationReportPluginExtension = project.extensions.findByName(ConfigurationReportPluginExtension.NAME) as ConfigurationReportPluginExtension

        val generator = when (extension.format) {
            ReportFormat.GRAPH_VIZ -> GraphVizConfigurationReportGenerator(extension.baseDir)
            ReportFormat.TEXT -> TextConfigurationReportGenerator()
        }

        val graph = readGraph(graphFile)
        generator.generate(graph)
    }

    @VisibleForTesting
    internal fun readGraph(graphFile: File): ConfigurationGraph {
        ObjectInputStream(FileInputStream(graphFile)).use { inFile ->
            val graph = inFile.readObject()

            //Cast it back into a Map
            when (graph) {
                is ConfigurationGraph -> return graph
                else -> throw IllegalStateException("Deserialization of congifuration graph failed!")
            }
        }
    }
}