package com.tomtresansky.gradle.plugin.configurationreport.html

import com.google.common.annotations.VisibleForTesting
import com.tomtresansky.gradle.plugin.configurationreport.BuildInfo
import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationGraph
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.io.File
import java.io.StringWriter
import java.net.URL
import java.util.*

/**
 * Formatter which uses Thymeleaf templates to generate pretty html configuration reports.
 */
class HTMLReportFormatter(private val pngFile: File,
                          private val configs: ConfigurationGraph) {
    companion object {
        // Should match this class' package, with trailing /
        const val TEMPLATES_PREFIX = "WEB-INF/templates/"
        const val TEMPLATES_SUFFIX = ".html"
        // Template file name does NOT include extension
        const val TEMPLATE_FILE_BASE_NAME = "report_template"
    }

    private val templateEngine = TemplateEngine()

    init {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.prefix = TEMPLATES_PREFIX
        templateResolver.suffix = TEMPLATES_SUFFIX
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.isCacheable = false

        templateEngine.setTemplateResolver(templateResolver)
    }

    fun format(): String {
        val vars = HTMLReportVariables.fromBuildInfoUsingCurrentDate(pngFile.name, configs, BuildInfo.instance())
        return format(vars)
    }

    @VisibleForTesting
    internal fun format(vars: HTMLReportVariables): String {
        val context = Context().apply { setVariables(vars.toMap())}

        val out = StringWriter()
        templateEngine.process(TEMPLATE_FILE_BASE_NAME, context, out)
        out.flush()

        return out.toString()
    }
}

internal data class HTMLReportVariables(val project: String,
                                        val graphImageFilename: String,
                                        val graph: ConfigurationGraph,
                                        val today: Calendar,
                                        val homepageURL: URL,
                                        val version: String,
                                        val commit: String) {
    companion object {
        fun fromBuildInfoUsingCurrentDate(graphImageFilename: String,
                                          graph: ConfigurationGraph,
                                          buildInfo: BuildInfo) : HTMLReportVariables {
            return HTMLReportVariables(graphImageFilename = graphImageFilename,
                                       project = buildInfo.projectName,
                                       graph = graph,
                                       today = Calendar.getInstance(),
                                       homepageURL = buildInfo.homepage,
                                       version = buildInfo.version,
                                       commit = buildInfo.gitCommit)
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf("project" to project,
                     "graphImageFilename" to graphImageFilename,
                     "graph" to graph,
                     "today" to today,
                     "homepageURL" to homepageURL,
                     "version" to version,
                     "commit" to commit)
    }
}
