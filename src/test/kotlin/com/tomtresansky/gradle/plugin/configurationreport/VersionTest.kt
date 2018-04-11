package com.tomtresansky.gradle.plugin.configurationreport

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import java.time.LocalDateTime
import java.time.Month
import java.time.ZonedDateTime
import java.util.*

/**
 * Unit tests for the [Version] class.
 *
 * @author Tom Tresansky
 */
object VersionTest : Spek({
    given("a Version object, initialized from a sample version.properties file") {
        it("has the expected version") {
            val expectedVersion = "0.0.1"
            assertThat(Version.projectVersion).isEqualTo(expectedVersion)
        }
        it("has the expected buildTime") {
            val expectedDate = Date.from(ZonedDateTime.of(LocalDateTime.of(2018, Month.APRIL, 11, 16, 55, 56), // This is in GMT, will be adjusted to EDT (-4 hours)
                                                          TimeZone.getTimeZone("EDT").toZoneId()).toInstant())
            assertThat(Version.buildTime).isEqualTo(expectedDate)
        }
        it("has the expected commit hash") {
            val expectedCommit = "753c75724b7ab48d7284354b13388d7dd8e079f2"
            assertThat(Version.gitCommit).isEqualTo(expectedCommit)
        }
    }
})
