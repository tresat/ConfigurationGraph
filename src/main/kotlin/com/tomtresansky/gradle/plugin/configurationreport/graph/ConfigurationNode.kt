package com.tomtresansky.gradle.plugin.configurationreport.graph

import java.io.Serializable

data class ConfigurationNode(val configName: String, val children: List<ConfigurationNode> = emptyList()) : Serializable {
    private constructor(builder: Builder) : this(builder.configName!!, builder.extensions.toList())

    class Builder {
        var configName: String? = null
        val extensions: MutableList<ConfigurationNode> = mutableListOf()

        fun build() = ConfigurationNode(this)
    }
}