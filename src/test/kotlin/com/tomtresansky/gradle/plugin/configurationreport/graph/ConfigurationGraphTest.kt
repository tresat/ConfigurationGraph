package com.tomtresansky.gradle.plugin.configurationreport.graph

import com.tomtresansky.gradle.plugin.configurationreport.util.getResourceFile
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals

const val SAMPLE_GRAPH_FILENAME = "sample_serialized_graph_1.graph"

object ConfigurationGraphTest: Spek({
   given("a configuration graph named Empty with no nodes") {
       val graph = ConfigurationGraph("Empty", emptyList())

       on("new") {
           it("should be equal to another empty graph") {
               assertEquals(ConfigurationGraph("Empty", emptyList()), graph)
           }
       }
   }

    given(description = "a serialized configuration graph file") {
        val sampleGraphFile = getResourceFile(ConfigurationGraph.javaClass, SAMPLE_GRAPH_FILENAME)

        on("load") {
            val graph = ConfigurationGraph.load(sampleGraphFile)

            it("should have properly deserialized the graph") {
                assertThat(graph).isNotNull()
            }
        }
    }
})
