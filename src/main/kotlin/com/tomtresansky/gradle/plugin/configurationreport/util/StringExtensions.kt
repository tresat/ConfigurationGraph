@file:Suppress("MatchingDeclarationName") // For EqualsLineByLineResult class
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

fun String.equalsLineByLine(other: String): EqualsLineByLineResult {
    val lines1 = lines()
    val lines2 = other.lines()

    if (lines1.size != lines2.size) {
        return EqualsLineByLineResult.DifferentSize(lines1.size, lines2.size)
    } else {
        for (i in 0 until lines1.size) {
            if (lines1[i] != lines2[i]) {
                return EqualsLineByLineResult.DifferentLines(i, lines1[i], lines2[i])
            }
        }
    }

    return EqualsLineByLineResult.Equal
}

sealed class EqualsLineByLineResult {
    object Equal: EqualsLineByLineResult()

    data class DifferentSize(val size1: Int, val size2: Int): EqualsLineByLineResult() {
        override fun toString(): String = "Strings do NOT have an equal amount of lines: $size1 vs. $size2!"
    }

    data class DifferentLines(val lineNo: Int, val line1: String, val line2: String): EqualsLineByLineResult() {
        override fun toString(): String = "Line $lineNo does NOT match!\n\t[$line1]\n\t[$line2]"
    }
}
