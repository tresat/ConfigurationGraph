package com.tomtresansky.gradle.plugin.configurationreport.internal

import org.gradle.api.artifacts.Configuration
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.mockito.Mockito.*

/*
 * Example syntax:
 *http://hadihariri.com/2016/10/04/Mocking-Kotlin-With-Mockito/
 *https://github.com/hhariri/mockito-sample/blob/master/src/test/kotlin/com/hadihariri/mockito/sample/test/LoanServiceTest.kt
val mockLoanCalculator = mock(LoanCalculator::class.java)
`when`(mockLoanCalculator.calculateAmount(3)).thenReturn(300.0)
 */
object ConfigurationNodeTest: Spek({
    given("a configuration node with no children named 'Test'") {
        val mockParent = mock(Configuration::class.java)
        val testName = "Test"
        `when`(mockParent.name).thenReturn(testName)
        val children = listOf<ConfigurationNode>()
        val node = ConfigurationNode(mockParent, children)

        on("toString") {
            val string = node.toString()

            it("should properly describe an empty node") {
                assertEquals("\tTest;", string)
            }
        }
    }
})
