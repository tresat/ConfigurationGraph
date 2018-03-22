package com.tomtresansky.gradle.plugin.configurationreport.html

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert

object HtmlBuilderTest : Spek({
    describe("a simple html page consisting of a head with a title and a body with some text in an h1") {
        val html = html {
            head {
                title {
                    +"Test Page"
                }
            }
            body {
                h1 {
                    +"Hello, World!"
                }
            }
        }

        it ("should be equivalent to the given string") {
            val expected = """
                |<html>
                |  <head>
                |    <title>
                |      Test Page
                |    </title>
                |  </head>
                |  <body>
                |    <h1>
                |      Hello, World!
                |    </h1>
                |  </body>
                |</html>
                |""".trimMargin()

            assertThat(html).isNotNull()
            assertThat(html.toString()).isNotNull()
                                       .isEqualTo(expected)
        }
    }
})