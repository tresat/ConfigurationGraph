package com.tomtresansky.gradle.plugin.configurationreport.internal.graphviz

import com.google.common.annotations.VisibleForTesting
import com.tomtresansky.gradle.plugin.configurationreport.internal.ConfigurationGraph
import com.tomtresansky.gradle.plugin.configurationreport.internal.ConfigurationNode
import com.tomtresansky.gradle.plugin.configurationreport.internal.IConfigurationGraphWriter
import java.io.OutputStream
import java.io.PrintWriter

class GraphVizConfigurationGraphWriter : IConfigurationGraphWriter {
    override fun writeGraph(graph: ConfigurationGraph, output: OutputStream) {
        PrintWriter(output).use { out ->
            out.println("digraph ${graph.title} {")
            graph.nodes.forEach { node ->
                out.println(stringifyNode(node))
            }
            out.println("}")
        }
    }

    @VisibleForTesting
    internal fun stringifyNode(node: ConfigurationNode): String {
        with (StringBuilder()) {
            append("\t${node.config.name}")

            when (node.children.size) {
                0 -> {} // do nothing
                1 -> append(" -> ")
                else -> append(" -> { ")
            }

            append(node.children.joinToString(separator = ", ") { e -> e.config.name })

            when (node.children.size) {
                0, 1 -> {} // do nothing
                else -> append(" }")
            }

            append(";")

            return toString()
        }
    }
}