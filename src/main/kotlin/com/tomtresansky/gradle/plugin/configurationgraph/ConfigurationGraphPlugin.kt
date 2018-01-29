package com.tomtresansky.gradle.plugin.configurationgraph

import org.gradle.api.plugins.ReportingBasePlugin
import org.gradle.api.Project
import org.gradle.api.internal.project.ProjectInternal
import java.io.File

class ConfigurationGraphPlugin : ReportingBasePlugin() {
    lateinit var extension: ConfigurationGraphPluginExtension
    lateinit var task: ExtractConfigurationsTask

    companion object {
        const val CONFIGURATIONS_GRAPH_REPORT_GROUP = "project"
    }

    override fun apply(project: ProjectInternal) {
        project.plugins.apply(ReportingBasePlugin::class.java)

        extension = createExtension(project)
        task = createTask(project)
    }

    fun createExtension(project: ProjectInternal): ConfigurationGraphPluginExtension {
        return project.extensions.create("configurationGraph", ConfigurationGraphPluginExtension::class.java, project)
    }

    fun createTask(project: ProjectInternal): ExtractConfigurationsTask {
        val task: ExtractConfigurationsTask = project.tasks.create(ExtractConfigurationsTask.TASK_NAME, ExtractConfigurationsTask::class.java)
        with (task) {
            description = ExtractConfigurationsTask.TASK_DESCRIPTION
            group = ExtractConfigurationsTask.TASK_GROUP

            outputFilePath = extension.outputFileName
        }

        return task
    }
}
