package by.andd3dfx.build

import org.gradle.api.Project
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

fun Project.root(version: String) {
    this.version = version

    allprojects {
        repositories {
            mavenLocal()
            maven(url = "https://plugins.gradle.org/m2/")
            mavenCentral()
        }
    }
}
