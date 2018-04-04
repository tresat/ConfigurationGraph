package com.tomtresansky.gradle.plugin.configurationreport.task

import com.google.common.annotations.VisibleForTesting
import com.tomtresansky.gradle.plugin.configurationreport.ConfigurationReportPluginExtension
import com.tomtresansky.gradle.plugin.configurationreport.ReportFormat
import com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz.GraphVizConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.engine.text.TextConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationGraph
import com.tomtresansky.gradle.plugin.configurationreport.graph.IConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.reporting.ConfigurationReportContainer
import com.tomtresansky.gradle.plugin.configurationreport.reporting.DefaultConfigurationReportContainer
import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.reporting.Reporting
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.reflect.Instantiator
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import javax.inject.Inject

/**
 * Task to output a report containing a graph of projects' configurations' information.
 *
 * All task classes must be defined as open.  If not set correctly, Gradle will try to proxy your class and fail the build.
 *
 * Constructor uses injected instantiator, retrieved via [ProjectInternal.getServices] to obtain an instance for [reports].
 */
open class ConfigurationReportTask @Inject constructor(instantiator: Instantiator) : DefaultTask(), Reporting<ConfigurationReportContainer> {
    companion object {
        const val TASK_NAME = "configurationReport"
        const val TASK_GROUP = "reporting"
        const val TASK_DESCRIPTION = "Generates an Html report about the project's configurations and their relationships."
    }

    private val reports: DefaultConfigurationReportContainer = instantiator.newInstance(DefaultConfigurationReportContainer::class.java, this)

    private val generator: IConfigurationReportGenerator
    init {
        val extension: ConfigurationReportPluginExtension = project.extensions.findByName(ConfigurationReportPluginExtension.NAME) as ConfigurationReportPluginExtension
        generator = when (extension.format) {
            ReportFormat.GRAPH_VIZ -> GraphVizConfigurationReportGenerator(extension.outputDir)
            ReportFormat.TEXT -> TextConfigurationReportGenerator()
        }
    }

    @InputFile
    lateinit var graphFile: File

    @OutputFile
    val reportFile: File = generator.reportFile

    @TaskAction
    fun generate() {
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

    override fun getReports(): ConfigurationReportContainer {
        return reports
    }

    override fun reports(closure: Closure<*>?): ConfigurationReportContainer {
        return reports.configure(closure!!) as ConfigurationReportContainer
    }

    override fun reports(configureAction: Action<in ConfigurationReportContainer>?): ConfigurationReportContainer {
        configureAction?.execute(reports)
        return reports
    }
}
