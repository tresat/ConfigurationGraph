package com.tomtresansky.gradle.plugin.configurationreport.graph

import java.io.Serializable

data class ConfigurationNode(val configName: String,
                             val transitive: Boolean = true,
                             val visible: Boolean = true,
                             val children: List<ConfigurationNode> = emptyList()) : Serializable {
    companion object {
        private const val serialVersionUID: Long = -1
    }

    private constructor(builder: Builder) : this(builder.configName!!,
                                                 builder.transitive,
                                                 builder.visible,
                                                 builder.extensions.toList())

    val transitiveChildren: List<ConfigurationNode> = children.filter { it.transitive }
    val intransitiveChildren: List<ConfigurationNode> = children.filter { !it.transitive }

    @Suppress("unused") // Used by template
    fun printTransitiveChildren(): String = transitiveChildren.joinToString(transform = { child -> child.configName})
    @Suppress("unused") // Used by template
    fun printIntransitiveChildren(): String = intransitiveChildren.joinToString(transform = { child -> child.configName})

    class Builder {
        var configName: String? = null
        var transitive: Boolean = true
        var visible: Boolean = true
        val extensions: MutableList<ConfigurationNode> = mutableListOf()

        fun build() = ConfigurationNode(this)
    }
}
