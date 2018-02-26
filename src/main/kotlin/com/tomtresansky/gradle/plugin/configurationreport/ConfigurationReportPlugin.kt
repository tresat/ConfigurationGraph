package com.tomtresansky.gradle.plugin.configurationreport

import com.tomtresansky.gradle.plugin.configurationreport.task.ConfigurationReportTask
import com.tomtresansky.gradle.plugin.configurationreport.task.ExtractConfigurationGraphTask
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.plugins.ProjectReportsPlugin
import org.gradle.api.plugins.ReportingBasePlugin

class ConfigurationReportPlugin : ReportingBasePlugin() {
    lateinit var extension: ConfigurationReportPluginExtension
    lateinit var task: ConfigurationReportTask

    override fun apply(project: ProjectInternal) {
        project.plugins.apply(ProjectReportsPlugin::class.java)

        extension = createExtension(project)
        task = createTask(project)
    }

    private fun createExtension(project: ProjectInternal): ConfigurationReportPluginExtension {
        return project.extensions.create(ConfigurationReportPluginExtension.NAME, ConfigurationReportPluginExtension::class.java, project)
    }

    private fun createTask(project: ProjectInternal): ConfigurationReportTask {
        val extractTask: ExtractConfigurationGraphTask = project.tasks.create(ExtractConfigurationGraphTask.TASK_NAME, ExtractConfigurationGraphTask::class.java)
        with (extractTask) {
            description = ExtractConfigurationGraphTask.TASK_DESCRIPTION
            group = ExtractConfigurationGraphTask.TASK_GROUP
        }

        val reportTask: ConfigurationReportTask = project.tasks.create(ConfigurationReportTask.TASK_NAME, ConfigurationReportTask::class.java)
        with (reportTask) {
            description = ConfigurationReportTask.TASK_DESCRIPTION
            group = ConfigurationReportTask.TASK_GROUP

            dependsOn(extractTask)
        }

        return reportTask
    }
}
