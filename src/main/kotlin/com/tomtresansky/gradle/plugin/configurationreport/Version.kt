package com.tomtresansky.gradle.plugin.configurationreport

import java.text.SimpleDateFormat
import java.util.*

/**
 * This singleton allows easy access via properties to build information about the current application.
 *
 * All propeties will be loaded from the file [VERSION_PROPERTIES_FILE_NAME] which must exist on the classpath in the
 * same package as this class as initialization time.
 *
 * @author Tom Tresansky
 */
object Version {
    private const val VERSION_PROPERTIES_FILE_NAME = "version.properties"

    private const val BUILD_TIME_PROP = "build.time"
    private const val GIT_COMMIT_PROP = "git.commit"
    private const val PROJECT_VERSION_PROP = "project.version"

    var props = Properties()
    init {
        val propsStream = Version::class.java.getResourceAsStream(VERSION_PROPERTIES_FILE_NAME)
        props.load(propsStream)
    }

    val projectVersion: String by lazy {
        props[PROJECT_VERSION_PROP] as String
    }

    val buildTime: Date by lazy {
        SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(props[BUILD_TIME_PROP] as String?)
    }

    val gitCommit: String by lazy {
        props[GIT_COMMIT_PROP] as String
    }
}