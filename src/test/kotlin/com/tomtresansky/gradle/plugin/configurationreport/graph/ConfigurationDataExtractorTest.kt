package com.tomtresansky.gradle.plugin.configurationreport.graph

import org.assertj.core.api.Assertions.assertThat
import org.gradle.api.artifacts.Configuration
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

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

       on("extracting data from a list of a single configuration") {
           val mockConfig = mock(Configuration::class.java).apply {
               `when`(this.name).thenReturn("Test")
               `when`(this.isTransitive).thenReturn(true)
               `when`(this.isVisible).thenReturn(true)
           }
           val configs = listOf(mockConfig)
           val configGraph = extractor.extractConfigurationData(configs)

           it("should properly describe the graph") {
               val configNode = ConfigurationNode(mockConfig.name, true, true, emptyList())

               assertThat(listOf(configNode)).containsAll(configGraph)
           }
       }

       on("extracting data from a list of a two parent -> child configurations") {
           val mockBaseConfig = mock(Configuration::class.java).apply {
               `when`(this.name).thenReturn("Base")
               `when`(this.isTransitive).thenReturn(true)
               `when`(this.isVisible).thenReturn(true)
           }
           val mockSubConfig = mock(Configuration::class.java).apply {
               `when`(this.name).thenReturn("Sub")
               `when`(this.extendsFrom).thenReturn(setOf(mockBaseConfig))
               `when`(this.isTransitive).thenReturn(true)
               `when`(this.isVisible).thenReturn(true)
           }
           val configs = listOf(mockSubConfig, mockBaseConfig) // Note we're feeding it non-alphabetized configs to test sorting
           val configGraph = extractor.extractConfigurationData(configs)

           it("should properly describe the graph") {
            val subConfigNode = ConfigurationNode(mockSubConfig.name, true, true, emptyList())
               val baseConfigNode = ConfigurationNode(mockBaseConfig.name, true, true, listOf(subConfigNode))

               assertThat(listOf(baseConfigNode, subConfigNode)).containsAll(configGraph)
           }
       }

       on("extracting data from a list of a three configurations with 1 parent and 2 children") {
           val mockBaseConfig = mock(Configuration::class.java).apply {
               `when`(this.name).thenReturn("Base")
               `when`(this.isTransitive).thenReturn(true)
               `when`(this.isVisible).thenReturn(true)
           }
           val mockSub1Config = mock(Configuration::class.java).apply {
               `when`(this.name).thenReturn("Sub1")
               `when`(this.extendsFrom).thenReturn(setOf(mockBaseConfig))
               `when`(this.isTransitive).thenReturn(true)
               `when`(this.isVisible).thenReturn(true)
           }
           val mockSub2Config = mock(Configuration::class.java).apply {
               `when`(this.name).thenReturn("Sub2")
               `when`(this.extendsFrom).thenReturn(setOf(mockBaseConfig))
               `when`(this.isTransitive).thenReturn(true)
               `when`(this.isVisible).thenReturn(true)
           }
           val configs = listOf(mockSub1Config, mockBaseConfig, mockSub2Config) // Note we're feeding it non-alphabetized configs to test sorting
           val configGraph = extractor.extractConfigurationData(configs)

           it("should properly describe the graph") {
               val sub1ConfigNode = ConfigurationNode(mockSub1Config.name, true, true, emptyList())
               val sub2ConfigNode = ConfigurationNode(mockSub2Config.name, true, true, emptyList())
               val baseConfigNode = ConfigurationNode(mockBaseConfig.name, true, true, listOf(sub1ConfigNode, sub2ConfigNode))

               assertThat(listOf(baseConfigNode, sub1ConfigNode, sub2ConfigNode)).containsAll(configGraph)
           }
       }
   }
})
