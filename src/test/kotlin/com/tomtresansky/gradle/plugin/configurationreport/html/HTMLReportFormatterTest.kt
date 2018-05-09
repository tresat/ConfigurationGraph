package com.tomtresansky.gradle.plugin.configurationreport.html

import com.tomtresansky.gradle.plugin.configurationreport.util.getResourceFile
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

const val SAMPLE_GRAPH_FILENAME = "sample_graph.png"
const val SAMPLE_REPORT_OUTPUT_1 = "sample_report_output_1.html"

object HTMLReportFormatterTest : Spek({
    given("an HTML formatter using a sample graph file") {
        val sampleGraphFile = getResourceFile(HTMLReportFormatterTest.javaClass, SAMPLE_GRAPH_FILENAME)
        val formatter = HTMLReportFormatter(sampleGraphFile, sampleGraphFile.toPath())

        on("format with dummy variables") {
            val testHeader = "TEST HEADER"
            val testMain = "TEST MAIN"
            val testToday = GregorianCalendar.from(ZonedDateTime.of(LocalDateTime.of(2018, Month.MAY, 2, 12, 51, 6),
                                                                    ZoneId.systemDefault()))
            val testVersion = "1.0.0"
            val testHomepage = "http://www.example.com"
            val testCommit = "753c75724b7ab48d7284354b13388d7dd8e079f2"

            val variables = mapOf("header" to testHeader,
                                  "main" to testMain,
                                  "today" to testToday,
                                  "version" to testVersion,
                                  "homepage" to testHomepage,
                                  "commit" to testCommit)

            val result = formatter.format(variables)

            it ("should produce correctly formatted html output") {
                val sampleReportFile1 = getResourceFile(HTMLReportFormatterTest.javaClass, SAMPLE_REPORT_OUTPUT_1)

                // Note that the th namespace declaration is also removed
                val expected = sampleReportFile1.readText()

                assertThat(result).isNotNull()
                                  .isEqualTo(expected)
            }
        }
    }
})
