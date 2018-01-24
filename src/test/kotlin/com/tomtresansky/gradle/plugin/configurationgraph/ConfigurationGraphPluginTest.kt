package com.tomtresansky.gradle.plugin.configurationgraph

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class ConfigurationGraphPluginTest {
    lateinit var project: Project

    @Before
    fun setUp() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply(ConfigurationGraphPlugin::class.java)
    }

    @Test
    fun `empty test`() {
        project.configurations
    }
}