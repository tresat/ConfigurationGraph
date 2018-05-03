package com.tomtresansky.gradle.plugin.configurationreport

import java.text.SimpleDateFormat
import java.util.*

/**
 * This singleton allows easy access via properties to build information about the current application.
 *
 * All propeties will be loaded from the file [BUILD_PROPERTIES_FILE_NAME] which must exist on the classpath in the
 * same package as this class as initialization time.
 *
 * @author Tom Tresansky
 */
object BuildInfo {
    private const val BUILD_PROPERTIES_FILE_NAME = "build.properties"

    private const val BUILD_TIME_PROP = "build.time"
    private const val GIT_COMMIT_PROP = "git.commit"
    private const val PROJECT_HOMEPAGE_PROP = "project.homepage"
    private const val PROJECT_NAME_PROP = "project.name"
    private const val PROJECT_VERSION_PROP = "project.version"

    var props = Properties()
    init {
        val propsStream = BuildInfo::class.java.getResourceAsStream(BUILD_PROPERTIES_FILE_NAME)
        props.load(propsStream)
    }

    val projectName: String by lazy {
        props[PROJECT_NAME_PROP] as String
    }

    val homepage: String by lazy {
        props[PROJECT_HOMEPAGE_PROP] as String
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
