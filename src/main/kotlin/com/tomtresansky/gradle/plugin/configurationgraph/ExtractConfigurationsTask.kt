package com.tomtresansky.gradle.plugin.configurationgraph

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * Task to output a file containing configuration graph information.
 *
 * The class must be defined as open.  If not set correctly, Gradle will try to proxy your class and fail the build.
 */
open class ExtractConfigurationsTask() : DefaultTask() {
    companion object {
        const val TASK_NAME = "extractConfigurations"
        const val TASK_GROUP = "reporting"
        const val TASK_DESCRIPTION = "Outputs a file containing information about the project's configurations and their relationships."
    }

    @OutputFile
    lateinit var outputFilePath: String

    @TaskAction
    fun extract() {
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