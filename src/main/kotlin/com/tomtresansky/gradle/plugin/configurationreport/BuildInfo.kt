package com.tomtresansky.gradle.plugin.configurationreport

import java.io.File
import java.io.FileInputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

/**
 * This class allows easy access via properties to build information about the current application.
 *
 * By default all properties will be loaded from the file [DEFAULT_PROPS_RELATIVE_PATH] which must exist on the classpath in the
 * same package as this class as initialization time.
 *
 * @author Tom Tresansky
 */
class BuildInfo(propsFile: File) {
    companion object {
        private const val DEFAULT_PROPS_RELATIVE_PATH = "META-INF/build/build.properties"
        var propsFile = File(DEFAULT_PROPS_RELATIVE_PATH)

        private const val BUILD_TIME_PROP = "build.time"
        private const val GIT_COMMIT_PROP = "git.commit"
        private const val PROJECT_HOMEPAGE_PROP = "project.homepage"
        private const val PROJECT_NAME_PROP = "project.name"
        private const val PROJECT_VERSION_PROP = "project.version"
    }

    constructor(): this(BuildInfo.propsFile)

    private val props = Properties()
    init {
        FileInputStream(propsFile).use { propsStream ->
            props.load(propsStream)
        }
    }

    val projectName: String by lazy {
        props[PROJECT_NAME_PROP] as String
    }

    val homepage: URL by lazy {
        URL(props[PROJECT_HOMEPAGE_PROP] as String?)
    }

    val version: String by lazy {
        props[PROJECT_VERSION_PROP] as String
    }

    val buildTime: Date by lazy {
        SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(props[BUILD_TIME_PROP] as String?)
    }

    val gitCommit: String by lazy {
        props[GIT_COMMIT_PROP] as String
    }
}
