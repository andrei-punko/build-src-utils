package by.andd3dfx.build

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestResult
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

        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {}
            override fun beforeTest(testDescriptor: TestDescriptor) {}
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}

            override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                if (suite.parent == null) { // root suite
                    println("Test result: ${result.resultType}")
                    println("Test summary: ${result.testCount} tests, " +
                            "${result.successfulTestCount} succeeded, " +
                            "${result.failedTestCount} failed, " +
                            "${result.skippedTestCount} skipped")
                }
            }
        })
    }
}
