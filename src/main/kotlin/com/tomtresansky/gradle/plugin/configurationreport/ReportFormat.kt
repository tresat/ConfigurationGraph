package com.tomtresansky.gradle.plugin.configurationreport

import com.tomtresansky.gradle.plugin.configurationreport.engine.text.TextConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.graph.IConfigurationReportGenerator
import com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz.GraphVizConfigurationReportGenerator
import kotlin.reflect.KClass

enum class ReportFormat {
    GRAPH_VIZ,
    TEXT;
}
