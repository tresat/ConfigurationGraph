package com.tomtresansky.gradle.plugin.configurationreport.internal

import org.gradle.api.artifacts.Configuration

// TODO: ideally this class uses some sort of renderer to create a dot, not just has it baked int
data class ConfigurationNode(val config: Configuration, private val extensions: List<ConfigurationNode>) {
    override fun toString(): String {
        with (StringBuilder()) {
            append("\t${config.name}")

            when (extensions.size) {
                0 -> {} // do nothing
                1 -> append(" -> ")
                else -> append(" -> { ")
            }

            append(extensions.joinToString(separator = ", ") { e -> e.config.name })
            append(";")

            return toString()
        }
    }
}