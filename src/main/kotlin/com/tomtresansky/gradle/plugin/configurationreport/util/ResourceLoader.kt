package com.tomtresansky.gradle.plugin.configurationreport.util

import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path

object ResourceLoader {
    fun getResourceFile(name: String): File {
        return File(ResourceLoader.javaClass.classLoader.getResource(name)?.file ?: throw FileNotFoundException("File $name could not be found in (from the root of the classpath)!"))
    }

    fun getResourceFile(path: Path): File {
        return getResourceFile(path.toString())
    }

    fun getResourceFile(clazz: Class<Any>, name: String): File {
        return File(clazz.classLoader.getResource(name)?.file ?: throw FileNotFoundException("File $name could not be found in the same package as ${clazz.name}!"))
    }
}
