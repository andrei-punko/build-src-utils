package by.andd3dfx.build

import org.gradle.api.Project

class Plugins(val project: Project) {

    fun springBoot() {
        project.apply {
            plugin("org.springframework.boot")
        }
    }

    companion object {
        fun Project.plugins(body: Plugins.() -> Unit) {
            Plugins(this).body()
        }
    }
}
