package com.github.lipinskipawel.testing

import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleTestingPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // Register a task
        project.tasks.register("testing") { task ->
            task.doLast {
                println("Hello from plugin 'testing'")
            }
        }
    }
}
