package com.github.lipinskipawel.testing

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertNotNull

class GradleTestingPluginTest {

    @Test
    fun `plugin registers task`() {
        // Create a test project and apply the plugin
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("java")
        project.plugins.apply("com.github.lipinskipawel.testing")

        // Verify the result
        assertNotNull(project.tasks.findByName("testIntegration"))
    }
}
