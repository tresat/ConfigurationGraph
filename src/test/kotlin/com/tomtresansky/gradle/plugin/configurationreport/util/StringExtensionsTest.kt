package com.tomtresansky.gradle.plugin.configurationreport.util

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals

object StringExtensionsTest : Spek({
    describe("String extension method useSystemLineSeparator") {
        val testCases = mapOf(
            "a\nb" to 'a' + System.lineSeparator() + 'b',
            "a\r\nb" to 'a' + System.lineSeparator() + 'b')

        testCases.forEach { inputValue, expectedValue ->
            on("a given String: $inputValue"){
                it("should resolve to $expectedValue after calling useSystemLineSeparator"){
                    val actualValue = inputValue.useSystemLineSeparator()
                    assertEquals(actualValue, expectedValue)
                }
            }
        }
    }
})
