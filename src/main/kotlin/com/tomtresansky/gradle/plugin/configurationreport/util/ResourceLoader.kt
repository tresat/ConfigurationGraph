package com.tomtresansky.gradle.plugin.configurationreport.util

import java.io.File
import java.io.FileNotFoundException

fun getResourceFile(clazz: Class<Any>, name: String): File {
    return File(clazz.getResource(name)?.file ?: throw FileNotFoundException("File $name could not be found in the same package as ${clazz.name}"))
}
