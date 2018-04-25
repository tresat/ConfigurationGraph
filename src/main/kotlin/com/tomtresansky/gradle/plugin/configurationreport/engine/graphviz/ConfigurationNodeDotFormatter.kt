package com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz

import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationNode

class ConfigurationNodeDotFormatter {
    fun format(node: ConfigurationNode): String {
        with (StringBuilder()) {
            append("\t${node.configName}")

            when (node.children.size) {
                0 -> {} // do nothing
                1 -> append(" -> ")
                else -> append(" -> { ")
            }

            append(node.children.joinToString(separator = ", ") { e -> e.configName })

            when (node.children.size) {
                0, 1 -> {} // do nothing
                else -> append(" }")
            }

            append(";")

            return toString()
        }
    }
}
