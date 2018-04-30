package com.tomtresansky.gradle.plugin.configurationreport.html

import com.google.common.annotations.VisibleForTesting
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.p
import kotlinx.html.stream.appendHTML
import kotlinx.html.title
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.io.File
import java.io.StringWriter
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

/**
 * Formatter which uses Thymeleaf templates to generate pretty html configuration reports.
 */
class HTMLReportFormatter(private val pngFile: File, private val reportDir: Path) {
    companion object {
        // Should match this class' package, with trailing /
        const val TEMPLATES_PREFIX = "com/tomtresansky/gradle/plugin/configurationreport/html/"
        const val TEMPLATES_SUFFIX = ".html"
        const val TEMPLATE_MODE = "HTML"
        // Template file name does NOT include extension
        const val TEMPLATE_FILE_BASE_NAME = "report_template"
    }

    private val templateEngine = TemplateEngine()

    init {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.prefix = TEMPLATES_PREFIX
        templateResolver.suffix = TEMPLATES_SUFFIX
        templateResolver.setTemplateMode(TEMPLATE_MODE)

        templateEngine.setTemplateResolver(templateResolver)
    }

    fun format(): String {
        val variables = mapOf("header" to "HEADER GOES HERE",
                              "main" to "MAIN GOES HERE",
                              "footer" to "FOOTER GOES HERE")

        return format(variables)
    }

    @VisibleForTesting
    internal fun format(vars : Map<String, String>): String {
        val context = Context(Locale.getDefault(), vars)

        val out = StringWriter()
        templateEngine.process(TEMPLATE_FILE_BASE_NAME, context, out)
        out.flush()

        return out.toString()
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
