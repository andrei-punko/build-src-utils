package by.andd3dfx.build

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.tasks.JacocoReport

fun Project.jacoco() {
    apply {
        plugin("org.gradle.jacoco")
    }

    tasks.withType<Test> {
        finalizedBy("jacocoTestReport")
    }
    tasks.withType<JacocoReport> {
        reports {
            html.required.set(true)
            xml.required.set(true)
            csv.required.set(true)
        }
    }
}
