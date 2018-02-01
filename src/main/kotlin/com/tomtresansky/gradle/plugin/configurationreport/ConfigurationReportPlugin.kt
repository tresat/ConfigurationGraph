package com.tomtresansky.gradle.plugin.configurationreport

import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.plugins.ProjectReportsPlugin
import org.gradle.api.plugins.ReportingBasePlugin
import java.nio.file.Paths

class ConfigurationReportPlugin : ReportingBasePlugin() {
    lateinit var extension: ConfigurationReportPluginExtension
    lateinit var task: ConfigurationReportTask

    companion object {
        const val CONFIGURATIONS_GRAPH_REPORT_GROUP = "project"
    }

    override fun apply(project: ProjectInternal) {
        project.plugins.apply(ProjectReportsPlugin::class.java)

        extension = createExtension(project)
        task = createTask(project)
    }

    private fun createExtension(project: ProjectInternal): ConfigurationReportPluginExtension {
        return project.extensions.create("configurationGraph", ConfigurationReportPluginExtension::class.java, project)
    }

    fun createTask(project: ProjectInternal): ConfigurationReportTask {
        val task: ConfigurationReportTask = project.tasks.create(ConfigurationReportTask.TASK_NAME, ConfigurationReportTask::class.java)
        with (task) {
            description = ConfigurationReportTask.TASK_DESCRIPTION
            group = ConfigurationReportTask.TASK_GROUP

            outputFilePath = Paths.get(extension.baseDir.path,
                                       ConfigurationReportPluginExtension.CONFIGURATION_GRAPH_REPORTS_DIR_NAME,
                                       extension.outputFileName)
        }

        return task
    }
}
