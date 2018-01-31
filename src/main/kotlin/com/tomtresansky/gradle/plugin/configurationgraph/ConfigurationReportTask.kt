package com.tomtresansky.gradle.plugin.configurationgraph

import org.gradle.api.Project
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.diagnostics.AbstractReportTask
import org.gradle.api.tasks.diagnostics.internal.TextReportRenderer
import java.nio.file.Path

/**
 * Task to output a report containing a graph of projects' configurations' information.
 *
 * The class must be defined as open.  If not set correctly, Gradle will try to proxy your class and fail the build.
 */
open class ConfigurationReportTask() : AbstractReportTask() {
    companion object {
        const val TASK_NAME = "configurationsGraph"
        const val TASK_GROUP = "reporting"
        const val TASK_DESCRIPTION = "Generates an HTML report about the project's configurations and their relationships."
    }

    private val renderer = TextReportRenderer()

    @OutputFile
    lateinit var outputFilePath: Path

    override fun getRenderer(): TextReportRenderer {
        return this.renderer
    }

    override fun generate(project: Project?) {
        extractConfigurationData(project!!)
    }

    fun extractConfigurationData(project: Project) {
        with(project) {
            val outputFile = file(outputFilePath).apply { createNewFile() }

            outputFile.printWriter().use { out ->
                configurations.forEach { c ->
                    out.println(c.name)
                    out.println("hey")
                }
            }
        }
    }
}