package com.tomtresansky.gradle.plugin.configurationreport.html

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import java.io.File
import java.io.FileNotFoundException
import com.tomtresansky.gradle.plugin.configurationreport.util.useSystemLineSeparator

const val SAMPLE_GRAPH_FILENAME = "sample_graph.png"

object HTMLReportFormatterTest : Spek({
    given("an HTML formatter using a sample graph file") {
        val sampleGraphFile = File(HTMLReportFormatter::class.java.getResource(SAMPLE_GRAPH_FILENAME)?.file ?: throw FileNotFoundException("$SAMPLE_GRAPH_FILENAME could not be found in the same package as ${HTMLReportFormatter::class.qualifiedName}"))
        val formatter = HTMLReportFormatter(sampleGraphFile, sampleGraphFile.toPath())

        on("format with dummy variables") {
            val testHeader = "TEST HEADER"
            val testMain = "TEST MAIN"
            val testFooter = "TEST FOOTER"
            val variables = mapOf("header" to testHeader,
                                  "main" to testMain,
                                  "footer" to testFooter)

            val result = formatter.format(variables).useSystemLineSeparator()

            it ("should produce correctly formatted html output") {
                // Note that the th namespace declaration is also removed
                val expected = """
                    |<!DOCTYPE html>
                    |<html>
                    |    <head>
                    |        <meta charset="UTF-8"/>
                    |        <title>Project Configurations Report</title>
                    |    </head>
                    |    <body>
                    |        <div id="header">$testHeader</div>
                    |        <div id="main">$testMain</div>
                    |        <div id="footer">$testFooter</div>
                    |    </body>
                    |</html>
                    |""".trimMargin().useSystemLineSeparator()

                assertThat(result).isNotNull()
                                  .isEqualTo(expected)
            }
        }
    }
})
