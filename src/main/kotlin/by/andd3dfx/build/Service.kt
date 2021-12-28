package by.andd3dfx.build

import org.gradle.api.Project
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

fun Project.service(
    group: String = "by.andd3dfx",
    version: String = rootProject.version.toString(),
    body: Project.() -> Unit = {}
) {
    this.group = group
    this.version = version

    javaProject()

    repositories {
        mavenLocal()
        maven(url = "https://plugins.gradle.org/m2/")
        mavenCentral()
    }

    test()

//    checkstyle()
    jacoco()
    this.body()
}
