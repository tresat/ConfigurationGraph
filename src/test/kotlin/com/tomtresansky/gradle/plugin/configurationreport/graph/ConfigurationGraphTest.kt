package com.tomtresansky.gradle.plugin.configurationreport.graph

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals

object ConfigurationGraphTest: Spek({
   given("a configuration graph named Empty with no nodes") {
       val graph = ConfigurationGraph("Empty", emptyList())

       on("new") {
           it("should be equal to another empty graph") {
               assertEquals(ConfigurationGraph("Empty", emptyList()), graph)
           }
       }
   }
})
