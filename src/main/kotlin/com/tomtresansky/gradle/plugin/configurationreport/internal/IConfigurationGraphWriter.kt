package com.tomtresansky.gradle.plugin.configurationreport.internal

import java.io.OutputStream

interface IConfigurationGraphWriter {
    fun writeGraph(graph: ConfigurationGraph, output: OutputStream)
}