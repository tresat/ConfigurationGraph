package com.tomtresansky.gradle.plugin.configurationreport.util

import java.io.File
import java.nio.file.Paths

object TestResources {
    const val SAMPLE_GRAPHS_DIRECTORY = "sample_graphs"
    const val SAMPLE_IMAGES_DIRECTORY = "sample_images"
    const val SAMPLE_REPORTS_DIRECTORY = "sample_reports"

    private const val SAMPLE_IMAGE_BASE_FILENAME= "sample_graph_"
    private const val SAMPLE_GRAPH_BASE_FILENAME = "sample_serialized_graph_"
    private const val SAMPLE_REPORT_BASE_FILENAME = "sample_report_output_"

    fun getSampleGraph(index: Int): File {
        return ResourceLoader.getResourceFile(Paths.get(SAMPLE_GRAPHS_DIRECTORY, "${SAMPLE_GRAPH_BASE_FILENAME}_${index}.png"))
    }

    private fun getSampleFile(index: )
}