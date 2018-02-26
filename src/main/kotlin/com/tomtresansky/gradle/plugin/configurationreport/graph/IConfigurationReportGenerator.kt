package com.tomtresansky.gradle.plugin.configurationreport.graph

import java.io.File
import java.io.OutputStream

interface IConfigurationReportGenerator {
    fun generate(graph: ConfigurationGraph): File
}