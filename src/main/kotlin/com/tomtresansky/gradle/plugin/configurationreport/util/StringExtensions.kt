package com.tomtresansky.gradle.plugin.configurationreport.util

import java.util.regex.Pattern

/**
 * Replace any newlines in this string with the System line separator.
 *
 * @return the converted String, with all newlines replaced with the system line separator value
 */
fun String.normalizeNewLines(): String {
    val sep = System.getProperty("line.separator")
    val eolRegex = Pattern.compile("\r?\n|(\\\\r)?\\\\n")

    return eolRegex.matcher(this).replaceAll(sep)
}

fun String.equalsIgnoringNewLines(other: String): Boolean {
    val normalizedThis = this.normalizeNewLines()
    val normalizedOther = other.normalizeNewLines()

    return normalizedThis.equals(normalizedOther)
}

fun assertEqualsIgnoringNewlines(s1: String, s2: String) {
    if (!s1.equalsIgnoringNewLines(s2)) {
        throw AssertionError("Strings are NOT equal (ignoring newlines)!\n\tS1 = [$s1]\n\tS2 = [$s2]")
    }
}
