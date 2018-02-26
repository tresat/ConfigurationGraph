package com.tomtresansky.gradle.plugin.configurationreport.graph

import org.gradle.api.artifacts.Configuration

data class ConfigurationNode(val config: Configuration, val children: List<ConfigurationNode> = emptyList()) {
    private constructor(builder: Builder) : this(builder.config!!, builder.extensions.toList())

    class Builder {
        var config: Configuration? = null
        val extensions: MutableList<ConfigurationNode> = mutableListOf()

        fun build() = ConfigurationNode(this)
    }
}