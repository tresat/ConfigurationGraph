package com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz

import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationNode
import org.gradle.api.artifacts.Configuration
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert
import org.mockito.Mockito

object ConfigurationNodeDotFormatterTest : Spek({
    given("a new ConfigurationNodeDotFormatter") {
        val formatter = ConfigurationNodeDotFormatter()

        on("calling format on a node with no children") {
            val mockEmptyConfig = Mockito.mock(Configuration::class.java).apply {
                Mockito.`when`(this.name).thenReturn("Empty")
            }
            val node = ConfigurationNode(mockEmptyConfig.name)

            val result = formatter.format(node)
            it("should produce the expected String") {
                Assert.assertEquals("\tEmpty;", result)
            }
        }

        on("calling format on a node with 1 child") {
            val mockChildConfig = Mockito.mock(Configuration::class.java).apply {
                Mockito.`when`(this.name).thenReturn("Child")
            }
            val mockParentConfig = Mockito.mock(Configuration::class.java).apply {
                Mockito.`when`(this.name).thenReturn("Parent")
                Mockito.`when`(this.extendsFrom).thenReturn(setOf(mockChildConfig))
            }
            val childNode = ConfigurationNode(mockChildConfig.name)
            val parentNode = ConfigurationNode(mockParentConfig.name, listOf(childNode))

            val result = formatter.format(parentNode)
            it("should produce the expected String") {
                Assert.assertEquals("\tParent -> Child;", result)
            }
        }

        on("calling format on a node with 2 children") {
            val mockChild1Config = Mockito.mock(Configuration::class.java).apply {
                Mockito.`when`(this.name).thenReturn("Child1")
            }
            val mockChild2Config = Mockito.mock(Configuration::class.java).apply {
                Mockito.`when`(this.name).thenReturn("Child2")
            }
            val mockParentConfig = Mockito.mock(Configuration::class.java).apply {
                Mockito.`when`(this.name).thenReturn("Parent")
                Mockito.`when`(this.extendsFrom).thenReturn(setOf(mockChild1Config, mockChild2Config))
            }
            val child1Node = ConfigurationNode(mockChild1Config.name)
            val child2Node = ConfigurationNode(mockChild2Config.name)
            val parentNode = ConfigurationNode(mockParentConfig.name, listOf(child1Node, child2Node))

            val result = formatter.format(parentNode)
            it("should produce the expected String") {
                Assert.assertEquals("\tParent -> { Child1, Child2 };", result)
            }
        }
    }
})
