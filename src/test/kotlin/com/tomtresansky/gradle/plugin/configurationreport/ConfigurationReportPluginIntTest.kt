package com.tomtresansky.gradle.plugin.configurationreport

import com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz.GraphVizConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.task.ConfigurationReportTask
import com.tomtresansky.gradle.plugin.configurationreport.task.ExtractConfigurationGraphTask
import org.junit.Assert.assertEquals
import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.SUCCESS
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.StringWriter
import java.nio.file.Paths

/**
 *
 * See inspiration at: https://github.com/vanniktech/gradle-dependency-graph-generator-plugin/blob/master/src/test/java/com/vanniktech/dependency/graph/generator/DependencyGraphGeneratorPluginTest.kt
 */
class ConfigurationReportPluginIntTest {
    companion object {
        const val GRADLE_VERSION = "4.6"
    }

    @get:Rule
    val testProjectDir = TemporaryFolder()

    @Test // TODO:Categorize me as slow or something
    fun runForBasicJavaProject() {
        testProjectDir.newFile("build.gradle").let { f ->
            f.writeText("""
                    |plugins {
                    |   id 'com.tomtresansky.gradle.plugin.configurationreport'
                    |}
                    |
                    |apply plugin: 'java'
                    |
                    |""".trimMargin())
        }

        val stdErrorWriter = StringWriter()

        val builder = GradleRunner.create()
                                  .withPluginClasspath();

        /*
          Need to append the buildInfo sourceSet's resource output to the classpath.

          Can't just adjust pluginSourceSet as in: https://docs.gradle.org/4.6/javadoc/org/gradle/plugin/devel/GradlePluginDevelopmentExtension.html
          since can only have 1 sourceSet there, and need main, and can't just add that resource to the main set, since it's generated.

          See also: https://docs.gradle.org/4.6/userguide/test_kit.html?_ga=2.250551430.1472596331.1527613600-1637288102.1442506414#sub:test-kit-classpath-injection
        */
        val pluginClasspath = builder.pluginClasspath.toMutableList()
        val buildInfoDir = File("build/resources/buildInfo").absoluteFile
        pluginClasspath.add(buildInfoDir)

        val result = builder.withPluginClasspath(pluginClasspath)
                            .withGradleVersion(GRADLE_VERSION)
                            .withProjectDir(testProjectDir.root)
                            .withArguments(ConfigurationReportTask.TASK_NAME)
                            .forwardStdError(stdErrorWriter)
                            .build()

        val extractTask = result.task(":${ExtractConfigurationGraphTask.TASK_NAME}")
        val reportTask = result.task(":${ConfigurationReportTask.TASK_NAME}")

        // Successful task executions
        assertEquals(SUCCESS, extractTask?.outcome);
        assertEquals(SUCCESS, reportTask?.outcome);

        // No errors output
        assertThat(stdErrorWriter).hasToString("")

        // Build output directory was created
        assertThat(File(testProjectDir.root, "build")).exists()
        // Reports output directory was created
        assertThat(File(testProjectDir.root, "build/reports")).exists()
        // Configuration Graph output directory was created
        val configGraphReportDir = File(testProjectDir.root, "build/reports/${ConfigurationReportPluginExtension.CONFIGURATION_GRAPH_REPORTS_DIR_NAME}")
        assertThat(configGraphReportDir).exists()

        // Configuration Graph output file was created
        val graphFile = Paths.get(configGraphReportDir.path, ExtractConfigurationGraphTask.DEFAULT_GRAPH_OUTPUT_FILE_NAME).toFile()
        assertThat(graphFile).exists()

        // Report file was created
        val reportFile = Paths.get(configGraphReportDir.path, GraphVizConfigurationReportGenerator.DEFAULT_REPORT_FILE_NAME).toFile()
        assertThat(reportFile).exists()
    }
}
