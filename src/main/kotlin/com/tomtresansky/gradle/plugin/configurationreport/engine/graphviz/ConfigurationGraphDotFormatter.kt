package com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz

import com.google.common.annotations.VisibleForTesting
import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationGraph
import java.io.File

/**
 *
 * Notes on the GraphViz dot format can be found here: https://graphviz.gitlab.io/_pages/pdf/libguide.pdf
 */
class ConfigurationGraphDotFormatter {
    val nodeFormatter = ConfigurationNodeDotFormatter()

    fun format(graph: ConfigurationGraph): String {
        with(StringBuilder()) {
            appendln("digraph ${graph.title} {")
            graph.nodes.forEach { node ->
                appendln(nodeFormatter.format(node))
            }
            appendln("}")

            return toString()
        }
    }
}