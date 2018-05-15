package com.tomtresansky.gradle.plugin.configurationreport.util

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.Assert.fail

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

    describe("String extension method equalsLineByLine") {
        data class TestCase(val line1: String, val line2: String, val matches: Boolean)

        val testCases = listOf(
            TestCase("Hello\nThere!", "Hello\nThere!", true),
            TestCase("Hello\nThere!", "\n", false),
            TestCase("Hello\nThere!", "Hi\nThere!", false),
            TestCase("Hello\nThere!", "Hello\nOver there!", false),
            TestCase("Hello\nThere!", "Hello\nThere!\nYou", false)
        )

        testCases.forEach { testCase ->
            if (testCase.matches) {
                on("a given pair of lines which should match") {
                    it("${testCase.line1} should be equalsLineByLine to ${testCase.line2}") {
                        assertEqualsLineByLine(testCase.line1, testCase.line2)
                    }
                }
            } else {
                on("a given pair of lines which should NOT match") {
                    it("${testCase.line1} should be equalsLineByLine to ${testCase.line2}") {
                        try {
                            assertEqualsLineByLine(testCase.line1, testCase.line2)
                            fail("Lines ${testCase.line1} should NOT match ${testCase.line2}")
                        } catch (e: AssertionError) {
                            // pass
                        }
                    }
                }
            }
        }
    }
})

fun assertEqualsIgnoringNewlines(expected: String, actual: String) {
    if (!expected.equalsIgnoringNewLines(actual)) {
        throw AssertionError("Strings are NOT equal (ignoring newlines)!\n\tS1 = [$expected]\n\tS2 = [$actual]")
    }
}

fun assertEqualsLineByLine(expected: String, actual: String) {
    val result = expected.equalsLineByLine(actual)
    when (result) {
        is EqualsLineByLineResult.Equal -> { /* pass */ }
        else -> throw AssertionError(result)
    }
}
