package com.tomtresansky.gradle.plugin.configurationreport.html

import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.p
import kotlinx.html.stream.appendHTML
import kotlinx.html.title
import java.io.File
import java.io.StringWriter
import java.nio.file.Path
import java.nio.file.Paths

class HTMLReportFormatter(private val pngFile: File, private val reportDir: Path) {
    companion object {
        const val TEMPLATE_FILE_NAME = "report_template.html"
    }

    fun format(): String {
        val reportTemplateFile = File(HTMLReportFormatter::class.java.getResource(TEMPLATE_FILE_NAME).toURI())
        val reportTemplate = reportTemplateFile.readText()

        return reportTemplate
    }

    private fun buildMainContents(): String {
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
