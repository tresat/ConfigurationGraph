package com.tomtresansky.gradle.plugin.configurationreport.internal.graphviz

import com.tomtresansky.gradle.plugin.configurationreport.internal.ConfigurationGraph
import com.tomtresansky.gradle.plugin.configurationreport.internal.ConfigurationNode
import org.gradle.api.artifacts.Configuration
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.io.ByteArrayOutputStream
import java.io.OutputStream

object GraphVizConfigurationGraphWriterTest: Spek({
    given("a new GraphVizConfigurationGraphWriter") {
        val writer = GraphVizConfigurationGraphWriter()

        on("calling stringifyNode on a node with no children") {
            val mockEmptyConfig = Mockito.mock(Configuration::class.java).apply {
                Mockito.`when`(this.name).thenReturn("Empty")
            }
            val node = ConfigurationNode(mockEmptyConfig)

            val result = writer.stringifyNode(node)
            it("should produce the expected String") {
                assertEquals("\tEmpty;", result)
            }
        }

        on("calling stringifyNode on a node with 1 child") {
            val mockChildConfig = Mockito.mock(Configuration::class.java).apply {
                `when`(this.name).thenReturn("Child")
            }
            val mockParentConfig = Mockito.mock(Configuration::class.java).apply {
                `when`(this.name).thenReturn("Parent")
                `when`(this.extendsFrom).thenReturn(setOf(mockChildConfig))
            }
            val childNode = ConfigurationNode(mockChildConfig)
            val parentNode = ConfigurationNode(mockParentConfig, listOf(childNode))

            val result = writer.stringifyNode(parentNode)
            it("should produce the expected String") {
                assertEquals("\tParent -> Child;", result)
            }
        }

        on("calling stringifyNode on a node with 2 children") {
            val mockChild1Config = Mockito.mock(Configuration::class.java).apply {
                `when`(this.name).thenReturn("Child1")
            }
            val mockChild2Config = Mockito.mock(Configuration::class.java).apply {
                `when`(this.name).thenReturn("Child2")
            }
            val mockParentConfig = Mockito.mock(Configuration::class.java).apply {
                `when`(this.name).thenReturn("Parent")
                `when`(this.extendsFrom).thenReturn(setOf(mockChild1Config, mockChild2Config))
            }
            val child1Node = ConfigurationNode(mockChild1Config)
            val child2Node = ConfigurationNode(mockChild2Config)
            val parentNode = ConfigurationNode(mockParentConfig, listOf(child1Node, child2Node))

            val result = writer.stringifyNode(parentNode)
            it("should produce the expected String") {
                assertEquals("\tParent -> { Child1, Child2 };", result)
            }
        }

        on("writing a graph with 2 empty nodes") {
            val mockEmptyConfig1 = Mockito.mock(Configuration::class.java).apply {
                Mockito.`when`(this.name).thenReturn("Empty1")
            }
            val mockEmptyConfig2 = Mockito.mock(Configuration::class.java).apply {
                Mockito.`when`(this.name).thenReturn("Empty2")
            }
            val node1 = ConfigurationNode(mockEmptyConfig1)
            val node2 = ConfigurationNode(mockEmptyConfig2)
            val graph = ConfigurationGraph("Test", listOf(node1, node2))

            val output = ByteArrayOutputStream()
            writer.writeGraph(graph, output)

            it("should produce the expected String") {
                output.flush()
                val result = output.toString()

                val expected = """
                    |digraph Test {
                    |${'\t'}Empty1;
                    |${'\t'}Empty2;
                    |}
                    |""".trimMargin()
                        .useSystemLineSeparator()


                assertEquals(expected, result)
            }
        }
    }
})

fun String.useSystemLineSeparator(): String {
    val sep = System.getProperty("line.separator")
    return this.replace("\n", sep)
}