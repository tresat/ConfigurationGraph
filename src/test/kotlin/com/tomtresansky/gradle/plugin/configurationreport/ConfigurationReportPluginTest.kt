package com.tomtresansky.gradle.plugin.configurationreport

import com.tomtresansky.gradle.plugin.configurationreport.task.ConfigurationReportTask
import com.tomtresansky.gradle.plugin.configurationreport.task.ExtractConfigurationGraphTask
import org.assertj.core.api.Assertions.assertThat
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class ConfigurationReportPluginTest {
    lateinit var project: Project

    @Before
    fun setUp() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply(ConfigurationReportPlugin::class.java)
    }

    @Test
    fun `extension added to project`() {
        assertThat(project.extensions.getByName(ConfigurationReportPluginExtension.NAME)).isNotNull()
                                                                                         .isInstanceOf(ConfigurationReportPluginExtension::class.java)
    }

    @Test
    fun `extract task added to project`() {
        assertThat(project.tasks.getByName(ExtractConfigurationGraphTask.TASK_NAME)).isNotNull()
                                                                                    .isInstanceOf(ExtractConfigurationGraphTask::class.java)
    }

    @Test
    fun `report task added to project and depends on extract task`() {
        val reportTask = project.tasks.getByName(ConfigurationReportTask.TASK_NAME)
        val extractTask = project.tasks.getByName(ExtractConfigurationGraphTask.TASK_NAME)

        assertThat(reportTask).isNotNull()
                              .isInstanceOf(ConfigurationReportTask::class.java)

        assertThat(reportTask.dependsOn.contains(extractTask))
    }
}