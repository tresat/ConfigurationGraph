package com.tomtresansky.gradle.plugin.configurationreport

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
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

    @OutputFile
    lateinit var outputFilePath: Path

    override fun getRenderer(): TextReportRenderer {
        return this.renderer
    }

    override fun generate(project: Project?) {
        val dotFile = extractConfigurationData(project!!)
    }

    private fun extractConfigurationData(project: Project): File {
        with(project) {
            val outputFile = file(outputFilePath).apply { createNewFile() }

            val configInheritanceMap : MutableMap<Configuration, MutableSet<Configuration>> = hashMapOf()
            configurations.forEach { childConfig ->
                // TODO("configs without extendsFrom need to be included here")
                childConfig.extendsFrom.map { parentConfig -> configInheritanceMap.computeIfAbsent(parentConfig, { _ -> hashSetOf() }) }
                                       .forEach { children -> children.add(childConfig) }
            }

            outputFile.printWriter().use { out ->
                out.println("digraph Configurations {")
                configInheritanceMap.forEach { parent, children ->
                    out.printf("\t${parent.name}")

                    when (children.size) {
                        0 -> out.print("\r\n")
                        1 -> out.print(" -> ")
                        else -> out.print(" -> { ")
                    }

                    out.print(children.map { it.name }
                                      .joinToString(separator = ", "))

                    if (children.size > 1) {
                        out.print(" }")
                    }

                    out.print(";\r\n")
                }
                out.println('}')
            }

            return outputFile
        }
    }
}