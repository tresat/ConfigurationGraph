package com.tomtresansky.gradle.plugin.configurationreport.engine.graphviz

import com.tomtresansky.gradle.plugin.configurationreport.graph.ConfigurationNode

class ConfigurationNodeDotFormatter {
    fun format(node: ConfigurationNode): String {
        with (StringBuilder()) {
            append("\t${node.configName};\n")

            addTransitiveEdges(node, this)
            addIntransitiveEdges(node, this)

            return toString()
        }
    }

    private fun addTransitiveEdges(node: ConfigurationNode,
                                   builder: StringBuilder) {
        addEdges(node, node.transitiveChildren, builder, EdgeStyle.TRANSITIVE)
    }

    private fun addIntransitiveEdges(node: ConfigurationNode,
                                     builder: StringBuilder) {
        addEdges(node, node.intransitiveChildren, builder, EdgeStyle.INSTRANSITIVE)
    }

    private fun addEdges(node: ConfigurationNode,
                         children: List<ConfigurationNode>,
                         builder: StringBuilder,
                         style: EdgeStyle) {
        if (children.isNotEmpty()) {
            with(builder) {
                append("\t${node.configName} -> ")
                when (children.size) {
                    1 -> { /* do nothing */ }
                    else -> append("{ ")
                }

                append(children.joinToString(separator = ", ") { e -> e.configName })

                when (children.size) {
                    1 -> { /* do nothing */ }
                    else -> append(" }")
                }

                append(" [style=${style.encoding}];\n")
            }
        }
    }

    enum class EdgeStyle(val encoding: String) {
        TRANSITIVE("solid"),
        INSTRANSITIVE("dashed")
    }
}
