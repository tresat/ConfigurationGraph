package com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz

import com.google.common.annotations.VisibleForTesting
import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationGraph
import com.tomtresansky.gradle.plugin.configurationreport.graph.IConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.html.HTMLReportFormatter
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import java.io.File
import java.nio.file.Paths

class GraphVizConfigurationReportGenerator(outputDir: File) : IConfigurationReportGenerator {
    companion object {
        const val DEFAULT_DOT_FILE_NAME = "configuration_graph.dot"
        const val DEFAULT_PNG_FILE_NAME = "configuration_graph.png"
        const val DEFAULT_REPORT_FILE_NAME = "configuration_report.html"

        const val GRAPH_HEIGHT = 1200
        const val GRAPH_WIDTH = 1200
    }

    override var reportFile = Paths.get(outputDir.path, DEFAULT_REPORT_FILE_NAME).toFile()
    var dotFile = Paths.get(outputDir.path, DEFAULT_DOT_FILE_NAME).toFile()
    var pngFile = Paths.get(outputDir.path, DEFAULT_PNG_FILE_NAME).toFile()

    override fun generate(graph: ConfigurationGraph): File {
        writeDotFile(graph, dotFile)
        writePngFile(dotFile, pngFile)
        writeReport(pngFile, reportFile)

        return reportFile
    }

    @VisibleForTesting
    internal fun writeDotFile(graph: ConfigurationGraph, dotFile: File) {
        val graphFormatter = ConfigurationGraphDotFormatter()

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
        val htmlReportFormatter = HTMLReportFormatter(pngFile, reportFile.parentFile.toPath())

        reportFile.createNewFile()
        reportFile.bufferedWriter().write(htmlReportFormatter.format())
    }
}
