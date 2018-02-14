package com.tomtresansky.gradle.plugin.configurationreport.internal

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer
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
object ConfigurationDataExtractorTest: Spek({
   given("a configuration data extractor") {
       val extractor = ConfigurationDataExtractor()

       on("extracting data from an empty list of configurations") {
           val configs = emptyList<Configuration>()
           val configGraph = extractor.extractConfigurationData(configs)

           it("should properly describe an empty graph") {
               assertEquals(emptyList<ConfigurationNode>(), configGraph)
           }
       }
   }
})
