package com.tomtresansky.gradle.plugin.configurationreport

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
    fun `empty test`() {
        project.configurations
    }
}