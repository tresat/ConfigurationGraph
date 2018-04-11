package com.tomtresansky.gradle.plugin.configurationreport.html

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.io.File
import java.io.StringWriter
import java.nio.file.Path
import java.nio.file.Paths

class HTMLReportFormatter(private val pngFile: File, private val reportDir: Path) {
    fun format(): String {
        val writer: StringWriter = StringWriter().let { writer ->
            writer.appendHTML().html {
                head {
                    title { +"Project Configurations" }
                }
                body {
                    h1 { +"XML encoding with Kotlin" }
                    p { +"this format can be used as an alternative markup to XML" }
                    p { +"Image link: ${buildRelativePathToPng()}" }
                }
            }
        }

        return writer.toString()
    }

    private fun buildRelativePathToPng(): Path {
        return Paths.get(reportDir.relativize(pngFile.toPath()).toString(), pngFile.name)
    }
}
