package by.andd3dfx.build

import org.gradle.api.Project

class Plugins(val project: Project) {

    fun springBoot() {
        project.apply {
            plugin("io.spring.dependency-management")
        }
    }

    companion object {
        fun Project.applyPlugins(body: Plugins.() -> Unit) {
            Plugins(this).body()
        }
    }
}
