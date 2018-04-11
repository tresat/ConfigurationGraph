package com.tomtresansky.gradle.plugin.configurationreport.html

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import java.io.File
import java.io.FileNotFoundException

const val SAMPLE_GRAPH_FILENAME = "sample_graph.png"

object HTMLReportFormatterTest : Spek({
    given("an HTML formatter using a sample graph file") {
        val sampleGraphFile = File(HTMLReportFormatter::class.java.getResource(SAMPLE_GRAPH_FILENAME)?.file ?: throw FileNotFoundException("$SAMPLE_GRAPH_FILENAME could not be found in the same package as ${HTMLReportFormatter::class.qualifiedName}"))
        val formatter = HTMLReportFormatter(sampleGraphFile, sampleGraphFile.toPath())

        on("format") {
            val result = formatter.format()

            it ("should produce correctly formatted html output") {
                val expected = """
                    |<html>
                    |  <head>
                    |    <title>Project Configurations</title>
                    |  </head>
                    |  <body>
                    |    <h1>XML encoding with Kotlin</h1>
                    |    <p>this format can be used as an alternative markup to XML</p>
                    |    <p>Image link: sample_graph.png</p>
                    |  </body>
                    |</html>
                    |""".trimMargin()

                assertThat(result).isNotNull()
                                  .isEqualTo(expected)
            }
        }
    }
})
