package com.github.lipinskipawel.testing

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSet.MAIN_SOURCE_SET_NAME
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test

class GradleTestingPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.withType(JavaPlugin::class.java) {
            val sourceSets = project.extensions.getByType(SourceSetContainer::class.java)
            val main = sourceSets.getByName(MAIN_SOURCE_SET_NAME)

            // this will automatically set up configurations like: testIntegrationImplementation, ...RuntimeOnly and more
            sourceSets.create("testIntegration") { testSourceSet ->
                testSourceSet.compileClasspath += main.output
                testSourceSet.runtimeClasspath += main.output
            }

            project.configurations.findByName("testIntegrationImplementation")
                ?.extendsFrom(project.configurations.getByName("implementation"))
            project.configurations.findByName("testIntegrationImplementation")
                ?.extendsFrom(project.configurations.getByName("testImplementation"))

            project.configurations.findByName("testIntegrationRuntimeOnly")
                ?.extendsFrom(project.configurations.getByName("runtimeOnly"))
            project.configurations.findByName("testIntegrationRuntimeOnly")
                ?.extendsFrom(project.configurations.getByName("testRuntimeOnly"))


            project.tasks.register("testIntegration", Test::class.java) { task ->
                task.description = "Runs integration tests."
                task.group = "verification"
                task.shouldRunAfter("test")

                // this will ensure that correct classpath is set during the test execution
                task.testClassesDirs = sourceSets.getByName("testIntegration").output.classesDirs
                task.classpath = sourceSets.getByName("testIntegration").runtimeClasspath

                task.useJUnitPlatform()
                task.testLogging {
                    it.events("passed", "failed", "skipped")
                }
            }
        }
    }
}
