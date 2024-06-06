package com.github.lipinskipawel.testing

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.NO_SOURCE
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class GradleTestingPluginFunctionalTest {

    @field:TempDir
    lateinit var projectDir: File

    private val buildFile by lazy { projectDir.resolve("build.gradle") }
    private val settingsFile by lazy { projectDir.resolve("settings.gradle") }

    @Test
    fun `can run task`() {
        // Set up the test build
        settingsFile.writeText("")
        buildFile.writeText(
            """
            plugins {
                id("java")
                id("com.github.lipinskipawel.testing")
            }
            """.trimIndent()
        )

        // Run the build
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("testIntegration")
        runner.withProjectDir(projectDir)
        val result = runner.build()

        // Verify the result
        assertEquals(result.task(":testIntegration")?.outcome, NO_SOURCE)
    }
}
