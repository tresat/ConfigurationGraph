package com.tomtresansky.gradle.plugin.configurationreport.graph

import java.io.Serializable

data class ConfigurationGraph(val title: String, val nodes: List<ConfigurationNode>) : Serializable
