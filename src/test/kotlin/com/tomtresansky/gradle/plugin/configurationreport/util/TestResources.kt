package com.tomtresansky.gradle.plugin.configurationreport.util

import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths

object TestResources {
    fun getSampleBuildProperties(index: Int): File = getSampleFile(index, TestResourceType.BUILD_INFO)
    fun getSampleImage(index: Int): File = getSampleFile(index, TestResourceType.IMAGE)
    fun getSampleGraph(index: Int): File = getSampleFile(index, TestResourceType.GRAPH)
    fun getSampleHtmlReport(index: Int): File = getSampleFile(index, TestResourceType.HTML_REPORT)

    private fun getSampleFile(index: Int, type: TestResourceType): File {
        val name = Paths.get(type.samplesDirectoryName, "${type.baseFileName}_$index.${type.extension}")
        return ResourceLoader.getResourceAsTempFile(name.toString())
    }

    private enum class TestResourceType(val samplesDirectoryName: String, val baseFileName: String, val extension: String) {
        BUILD_INFO("build_info", "sample_build_properties", "properties"),
        IMAGE("image", "sample_graph", "png"),
        GRAPH("graph", "sample_serialized_graph", "graph"),
        HTML_REPORT("report", "sample_report_output", "html")
    }
}
