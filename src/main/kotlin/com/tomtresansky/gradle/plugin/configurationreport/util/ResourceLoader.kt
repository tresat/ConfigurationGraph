package com.tomtresansky.gradle.plugin.configurationreport.util

import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path

object ResourceLoader {
    fun getResourceFile(relativePath: String): File {
        val classLoader = ResourceLoader.javaClass.classLoader
        val resource = classLoader.getResource(relativePath)
        val result = File(resource?.path)

        if (result.exists()) {
            return result
        } else {
            throw FileNotFoundException("File $relativePath could not be found (from the root of the classpath used by: ${classLoader.toString()})!")
        }
    }
}
