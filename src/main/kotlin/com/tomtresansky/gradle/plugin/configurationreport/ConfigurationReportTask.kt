package com.tomtresansky.gradle.plugin.configurationreport

import com.tomtresansky.gradle.plugin.configurationreport.internal.ConfigurationDataExtractor
import com.tomtresansky.gradle.plugin.configurationreport.internal.ConfigurationNode
import org.gradle.api.Project
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.diagnostics.AbstractReportTask
import org.gradle.api.tasks.diagnostics.internal.TextReportRenderer
import java.io.File
import java.nio.file.Path

/**
 * Task to output a report containing a graph of projects' configurations' information.
 *
 * The class must be defined as open.  If not set correctly, Gradle will try to proxy your class and fail the build.
 */
open class ConfigurationReportTask : AbstractReportTask() {
    companion object {
        const val TASK_NAME = "configurationReport"
        const val TASK_GROUP = "reporting"
        const val TASK_DESCRIPTION = "Generates an HTML report about the project's configurations and their relationships."
    }

    private val renderer = TextReportRenderer()
    private val extractor = ConfigurationDataExtractor()

    @OutputFile
    lateinit var outputFilePath: Path

    override fun getRenderer(): TextReportRenderer {
        return this.renderer
    }

    override fun generate(project: Project?) {
        val configInfo = extractor.extractConfigurationData(project ?: throw NullPointerException("project is null"))
        writeDotFile(project, configInfo)
    }

    private fun writeDotFile(project: Project, configInfo : List<ConfigurationNode>): File {
        with (project) {
            val outputFile = file(outputFilePath).apply { createNewFile() }

            outputFile.printWriter().use { out ->
                out.println("digraph Configurations {")
                configInfo.forEach { node ->
                    out.println(node)
                }
                out.println('}')
            }

            return outputFile
        }
    }
}