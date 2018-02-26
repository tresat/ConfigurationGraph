package com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz

import com.google.common.annotations.VisibleForTesting
import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationGraph
import com.tomtresansky.gradle.plugin.configurationreport.graph.IConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.html.html
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import java.io.File
import java.nio.file.Paths

class GraphVizConfigurationReportGenerator(val outputDir: File) : IConfigurationReportGenerator {
    companion object {
        const val DEFAULT_DOT_FILE_NAME = "configuration_graph.dot"
        const val DEFAULT_PNG_FILE_NAME = "configuration_graph.png"
        const val DEFAULT_REPORT_FILE_NAME = "configuration_report.html"

        const val GRAPH_HEIGHT = 600
        const val GRAPH_WIDTH = 600
    }

    var dotFileName = DEFAULT_DOT_FILE_NAME
    var pngFileName = DEFAULT_PNG_FILE_NAME
    var reportFileName = DEFAULT_REPORT_FILE_NAME

    val graphFormatter = ConfigurationGraphDotFormatter()

    override fun generate(graph: ConfigurationGraph): File {
        val dotFile = Paths.get(outputDir.path, dotFileName).toFile()
        writeDotFile(graph, dotFile)

        val pngFile = Paths.get(outputDir.path, pngFileName).toFile()
        writePngFile(dotFile, pngFile)

        val reportFile = Paths.get(outputDir.path, reportFileName).toFile()
        writeReport(pngFile, reportFile)

        return reportFile
    }

    @VisibleForTesting
    internal fun writeDotFile(graph: ConfigurationGraph, dotFile: File) {
        dotFile.createNewFile()
        dotFile.bufferedWriter().use { out ->
            out.write(graphFormatter.format(graph))
            out.flush()
        }
    }

    @VisibleForTesting
    internal fun writePngFile(dotFile: File, pngFile: File) {
        pngFile.createNewFile()
        Graphviz.fromFile(dotFile)
                .height(GRAPH_HEIGHT)
                .width(GRAPH_WIDTH)
                .render(Format.PNG)
                .toFile(pngFile)
    }

    @VisibleForTesting
    internal fun writeReport(pngFile: File, reportFile: File) {
        reportFile.createNewFile()
        reportFile.bufferedWriter().use { writer ->
            val html = html {
                head {
                    title { +"Project Configurations" }
                }
                body {
                    h1 { +"XML encoding with Kotlin" }
                    p { +"this format can be used as an alternative markup to XML" }
                    p { +"Image link: ${pngFile.path}" }
                }
            }

            writer.write(html.toString())
            writer.flush()
        }
    }
}