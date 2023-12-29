package by.andd3dfx.build

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.withType

fun Project.test(showStacktrace: Boolean = true, excludeTags: List<String> = listOf("integration")) {
    tasks.withType<Test> {
        useJUnitPlatform {
            excludeTags(*excludeTags.toTypedArray())
        }
        if (showStacktrace) {
            testLogging {
                events("passed", "skipped", "failed", "standardOut", "standardError")
                showStackTraces = true
                exceptionFormat = TestExceptionFormat.FULL
            }
        }
    }
}
