package com.tomtresansky.gradle.plugin.configurationreport.util

fun String.useSystemLineSeparator(): String {
    val sep = System.getProperty("line.separator")
    return this.replace("\n", sep)
}
