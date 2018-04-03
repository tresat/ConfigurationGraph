package com.tomtresansky.gradle.plugin.configurationreport

import com.tomtresansky.gradle.plugin.configurationreport.task.ConfigurationReportTask
import com.tomtresansky.gradle.plugin.configurationreport.task.ExtractConfigurationGraphTask
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.plugins.ProjectReportsPlugin
import org.gradle.api.plugins.ReportingBasePlugin
import java.io.File
import java.nio.file.Paths

class ConfigurationReportPlugin : ReportingBasePlugin() {
    lateinit var extension: ConfigurationReportPluginExtension
    lateinit var extractTask: ExtractConfigurationGraphTask
    lateinit var reportTask: ConfigurationReportTask

    override fun apply(project: ProjectInternal) {
        project.plugins.apply(ProjectReportsPlugin::class.java)
        project.plugins.apply(ReportingBasePlugin::class.java)

        extension = createExtension(project)
        extractTask = createExtractTask(project)
        reportTask = createReportTask(project)
    }

    private fun createExtension(project: ProjectInternal): ConfigurationReportPluginExtension {
        return project.extensions.create(ConfigurationReportPluginExtension.NAME, ConfigurationReportPluginExtension::class.java, project)
    }

    private fun createExtractTask(project: ProjectInternal): ExtractConfigurationGraphTask {
        val extractTask: ExtractConfigurationGraphTask = project.tasks.create(ExtractConfigurationGraphTask.TASK_NAME,
                                                                              ExtractConfigurationGraphTask::class.java)
        with(extractTask) {
            description = ExtractConfigurationGraphTask.TASK_DESCRIPTION
            group = ExtractConfigurationGraphTask.TASK_GROUP

            graphFile = Paths.get(extension.outputDir.path, ExtractConfigurationGraphTask.DEFAULT_GRAPH_OUTPUT_FILE_NAME).toFile()
        }

        return extractTask
    }

    private fun createReportTask(project: ProjectInternal): ConfigurationReportTask {
        val reportTask: ConfigurationReportTask = project.tasks.create(ConfigurationReportTask.TASK_NAME, ConfigurationReportTask::class.java)
        with (reportTask) {
            description = ConfigurationReportTask.TASK_DESCRIPTION
            group = ConfigurationReportTask.TASK_GROUP

            dependsOn(extractTask)

            graphFile = extractTask.graphFile
        }

        return reportTask
    }
}
