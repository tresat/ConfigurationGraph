package com.tomtresansky.gradle.plugin.configurationreport.html

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.io.File
import java.io.FileNotFoundException

object HtmlBuilderTest : Spek({
    val SAMPLE_GRAPH_FILENAME = "sample_graph.png"
    lateinit var sampleGraphFile: File

    describe("an HTML formatter using a sample graph file") {
        beforeGroup {
            val pathToSampleGraphFile = HTMLReportFormatter::class.java.getResource(SAMPLE_GRAPH_FILENAME)?.file ?: throw FileNotFoundException("${SAMPLE_GRAPH_FILENAME} could not be found in the same package as ${HTMLReportFormatter::class.qualifiedName}")
            sampleGraphFile = File(pathToSampleGraphFile)
        }

        val formatter = HTMLReportFormatter(sampleGraphFile)

        on("format") {
            val result = formatter.format()

            it ("should produce correctly formatted html output") {
                /*val expected = """
                    |<html>
                    |  <head>
                    |    <title>
                    |      Test Page
                    |    </title>
                    |  </head>
                    |  <body>
                    |    <h1>
                    |      Hello, World!
                    |    </h1>
                    |  </body>
                    |</html>
                    |""".trimMargin()

                assertThat(result).isNotNull()
                                  .isEqualTo(expected)*/
            }
        }
    }
})
