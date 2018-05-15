package com.tomtresansky.gradle.plugin.configurationreport.graph

import org.assertj.core.api.Assertions.assertThat
import org.gradle.api.artifacts.Configuration
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.mockito.Mockito.*

object ConfigurationNodeTest: Spek({
    given("a ConfigurationNode.Builder") {
        val builder = ConfigurationNode.Builder()

        on("building a new node from a childless Configuration named Empty") {
            val mockConfig = mock(Configuration::class.java).apply {
                `when`(this.name).thenReturn("Empty")
            }
            builder.configName = mockConfig.name

            val result = builder.build()

            it("should have properly built an empty, transitive node named Empty") {
                assertEquals("Empty", result.configName)
                assertTrue(result.transitive)
                assertThat(result.children).isNotNull()
                                           .isEmpty()
                assertThat(result.transitiveChildren).isNotNull()
                                                     .isEmpty()
                assertThat(result.intransitiveChildren).isNotNull()
                                                       .isEmpty()
            }
        }
    }
})
