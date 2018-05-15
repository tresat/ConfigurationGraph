package com.tomtresansky.gradle.plugin.configurationreport.graph

import com.google.common.annotations.VisibleForTesting
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.io.Serializable

data class ConfigurationGraph(val title: String, val nodes: List<ConfigurationNode>) : Serializable {
    companion object {
        private const val serialVersionUID: Long = -1

        fun load(graphFile: File): ConfigurationGraph {
            ObjectInputStream(FileInputStream(graphFile)).use { inFile ->
                val graph = inFile.readObject()

                //Cast it back into a Map
                when (graph) {
                    is ConfigurationGraph -> return graph
                    else -> throw IllegalStateException("Deserialization of congifuration graph failed!")
                }
            }
        }
    }
}
