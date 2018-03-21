package com.tomtresansky.gradle.plugin.configurationreport.engine.text

import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationGraph
import com.tomtresansky.gradle.plugin.configurationreport.graph.IConfigurationReportGenerator
import java.io.File
import java.io.OutputStream

class TextConfigurationReportGenerator : IConfigurationReportGenerator {
    override var reportFile: File
        get() = TODO(
            "not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun generate(graph: ConfigurationGraph): File {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
