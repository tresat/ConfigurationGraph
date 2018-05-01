package com.tomtresansky.gradle.plugin.configurationreport.util

/**
 * Replace any newlines in this string with the System line separator if they are <strong>not</strong> already that.
 *
 * @return the converted String, with all newlines replaced with the system line separator value
 */
fun String.useSystemLineSeparator(): String {
    val sep = System.getProperty("line.separator")

    return if ("\r\n".equals(sep)) {
        val regex = Regex("[^\\r]\\n")
        regex.replace(this) { m ->
            m.value[0] + "\r" + m.value[1]
        }
    } else {
        this
    }
}
