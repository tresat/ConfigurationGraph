package com.tomtresansky.gradle.plugin.configurationreport.util

import com.google.common.base.Preconditions
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object ResourceLoader {
    private const val RESOURCE_DIR_PREFIX = "resource"

    /**
     * Get an [InputStream] to the specified resource.
     *
     * Note that this method is intended to retrieve resources from within that jar that this project will
     * create.  So it must *NOT* utilize [ClassLoader.getResource], which will fail to extract files from
     * inside jars on the classpath.  Instead, use [ClassLoader.getResourceAsStream].
     *
     * @param relativePath complete path from root of the classpath to a resource file, or a file within a jar on the classpath
     */
    fun getResource(relativePath: String): InputStream {
        Preconditions.checkArgument(!relativePath.isBlank(), "relativePath must NOT be null!")

        val classLoader = Thread.currentThread().contextClassLoader
        val result = classLoader.getResourceAsStream(relativePath)

        if (null != result) {
            return result
        } else {
            throw FileNotFoundException("File $relativePath could not be found (from the root of the classpath used by: ${classLoader.toString()})!")
        }
    }

    fun getResourceAsTempFile(relativePath: String): File {
        val fileName = Paths.get(relativePath).fileName.toString()
        val tempDirectory = Files.createTempDirectory(RESOURCE_DIR_PREFIX)
        val resourceFile = Files.createFile(Paths.get(tempDirectory.toString(), fileName)).toFile()

        val inputStream = getResource(relativePath)
        val outputStream = FileOutputStream(resourceFile)
        inputStream.copyTo(outputStream)

        return resourceFile
    }
}
