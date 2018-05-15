package com.tomtresansky.gradle.plugin.configurationreport.html

import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationGraph
import com.tomtresansky.gradle.plugin.configurationreport.util.getResourceFile
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.net.URL
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

const val SAMPLE_IMAGE_FILENAME = "sample_graph.png"
const val SAMPLE_GRAPH_FILENAME = "sample_serialized_graph_1.graph"
const val SAMPLE_REPORT_OUTPUT_1 = "sample_report_output_1.html"

@Suppress("unused")
object HTMLReportFormatterTest : Spek({
    given("an HTML formatter using a sample graph file") {
        val sampleImageFile = getResourceFile(HTMLReportFormatter.javaClass, SAMPLE_IMAGE_FILENAME)
        val sampleGraph = ConfigurationGraph.load(getResourceFile(ConfigurationGraph.javaClass, SAMPLE_GRAPH_FILENAME))

        val formatter = HTMLReportFormatter(sampleImageFile,
                                            sampleGraph)

        on("format with dummy variables") {
            val testProject = "Sample Project"
            val testToday = GregorianCalendar.from(ZonedDateTime.of(LocalDateTime.of(2018, Month.MAY, 2, 12, 51, 6),
                                                                    ZoneId.systemDefault()))
            val testVersion = "1.0.0"
            val testHomepage = URL("http://www.example.com")
            val testCommit = "753c75724b7ab48d7284354b13388d7dd8e079f2"

            val variables = HTMLReportVariables(project = testProject,
                                                graphImageFilename = sampleImageFile.name,
                                                graph = sampleGraph,
                                                today = testToday,
                                                homepageURL = testHomepage,
                                                version = testVersion,
                                                commit = testCommit)

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
