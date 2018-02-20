package com.tomtresansky.gradle.plugin.configurationreport.internal

import org.gradle.api.artifacts.Configuration

// TODO: ideally this class uses some sort of renderer to create a dot, not just has it baked int
data class ConfigurationNode(val config: Configuration, private val children: List<ConfigurationNode>) {
    override fun toString(): String {
        with (StringBuilder()) {
            append("\t${config.name}")

            when (children.size) {
                0 -> {} // do nothing
                1 -> append(" -> ")
                else -> append(" -> { ")
            }

            append(children.joinToString(separator = ", ") { e -> e.config.name })
            append(";")

            return toString()
        }
    }

    constructor(config: Configuration): this(config, emptyList())

    private constructor(builder: Builder) : this(builder.config!!, builder.extensions.toList())

    class Builder {
        var config: Configuration? = null
        val extensions: MutableList<ConfigurationNode> = mutableListOf()

        fun build() = ConfigurationNode(this)
    }
}