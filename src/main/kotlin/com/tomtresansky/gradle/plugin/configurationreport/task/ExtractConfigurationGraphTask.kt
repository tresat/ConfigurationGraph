package com.tomtresansky.gradle.plugin.configurationreport.task

import com.tomtresansky.gradle.plugin.configurationreport.ConfigurationReportPluginExtension
import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationDataExtractor
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


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
    }

    private val extractor = ConfigurationDataExtractor()

    @TaskAction
    fun extract() {
        val graph = extractor.extractConfigurationData(project)
        val extension: ConfigurationReportPluginExtension = project.extensions.findByName(ConfigurationReportPluginExtension.NAME) as ConfigurationReportPluginExtension

        extension.graph = graph
    }
}