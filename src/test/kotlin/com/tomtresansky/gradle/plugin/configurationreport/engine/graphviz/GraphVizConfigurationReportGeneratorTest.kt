package com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz

import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationGraph
import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationNode
import org.gradle.api.artifacts.Configuration
import org.gradle.internal.impldep.com.google.common.io.Files
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.mockito.Mockito

object GraphVizConfigurationReportGeneratorTest : Spek({

    given("a new GraphVizConfigurationReportGenerator") {
        val generator = GraphVizConfigurationReportGenerator(Files.createTempDir())

        on("writing a graph with 2 empty nodes") {
            val mockEmptyConfig1 = Mockito.mock(Configuration::class.java).apply {
                Mockito.`when`(this.name).thenReturn("Empty1")
            }
            val mockEmptyConfig2 = Mockito.mock(Configuration::class.java).apply {
                Mockito.`when`(this.name).thenReturn("Empty2")
            }
            val node1 = ConfigurationNode(mockEmptyConfig1.name)
            val node2 = ConfigurationNode(mockEmptyConfig2.name)
            val graph = ConfigurationGraph("Test", listOf(node1, node2))

            val outputFile = generator.generate(graph)

            it("should produce a report file") {
                assertTrue(outputFile.exists())
            }
        }
    }
})

fun String.useSystemLineSeparator(): String {
    val sep = System.getProperty("line.separator")
    return this.replace("\n", sep)
}