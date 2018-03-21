package com.tomtresansky.gradle.plugin.configurationreport.task

import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationDataExtractor
import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.internal.artifacts.configurations.DefaultConfiguration
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

/**
 * Task to generate a graph of projects' configurations' information and store it in the extension object.
 *
 * All task classes must be defined as open.  If not set correctly, Gradle will try to proxy your class and fail the build.
 */
open class ExtractConfigurationGraphTask : DefaultTask() {
    companion object {
        const val TASK_NAME = "extractConfigurationGraph"
        const val TASK_GROUP = "reporting"
        const val TASK_DESCRIPTION = "Extracts data about the project's configurations and their relationships."

        const val DEFAULT_GRAPH_OUTPUT_FILE_NAME = "configuration_graph.graph"
    }

    private val extractor = ConfigurationDataExtractor()

    @OutputFile
    lateinit var graphFile: File

    @Input
    private val graph = extractor.extractConfigurationData(project)

    @TaskAction
    fun extract() {
        graphFile.createNewFile()

        // Serialize the graph to a file
        ObjectOutputStream(FileOutputStream(graphFile)).use{ out ->
            out.writeObject(graph)
        }
    }
}