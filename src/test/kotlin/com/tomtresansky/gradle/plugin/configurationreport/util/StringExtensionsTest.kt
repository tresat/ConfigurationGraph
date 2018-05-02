package com.tomtresansky.gradle.plugin.configurationreport.util

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals

object StringExtensionsTest : Spek({
    describe("String extension method normalizeNewLines") {
        val testCases = mapOf(
            "a\nb" to 'a' + System.lineSeparator() + 'b',
            "a\r\nb" to 'a' + System.lineSeparator() + 'b')

        testCases.forEach { inputValue, expectedValue ->
            on("a given String: $inputValue"){
                it("$inputValue should be equalsIgnoringNewLines to $expectedValue") {
                    assertEqualsIgnoringNewlines(inputValue, expectedValue)
                }

                it("should resolve to $expectedValue after calling normalizeNewLines"){
                    val actualValue = inputValue.normalizeNewLines()
                    assertEquals(actualValue, expectedValue)
                }
            }
        }
    }
})
