package com.tomtresansky.gradle.plugin.configurationreport.html

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.io.File
import java.io.StringWriter

class HTMLReportFormatter(val pngFile: File) {
    fun format(): String {
        val writer: StringWriter = StringWriter().let { writer ->
            writer.appendHTML().html {
                head {
                    title { +"Project Configurations" }
                }
                body {
                    h1 { +"XML encoding with Kotlin" }
                    p { +"this format can be used as an alternative markup to XML" }
                    p { +"Image link: ${pngFile.path}" }
                }
            }
        }

        return writer.toString()
    }
}
