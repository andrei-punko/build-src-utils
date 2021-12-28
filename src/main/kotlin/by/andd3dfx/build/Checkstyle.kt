package by.andd3dfx.build

import org.gradle.api.Project
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.kotlin.dsl.configure

fun Project.checkstyle(confFile: String = "checkstyle/conf.xml") {
    apply {
        plugin("checkstyle")
    }
    configure<CheckstyleExtension> {
        val projectConfig = file("${rootProject.projectDir}/$confFile")
        if (projectConfig.exists()) {
            configFile = projectConfig
            configDirectory.set(projectConfig.parentFile)
        } else {
            configFile = file("${rootProject.projectDir}/buildSrc/$confFile")
            configDirectory.set(configFile.parentFile)
        }
    }
}
