package by.andd3dfx.build

import org.gradle.api.Project

class DependencyVersions(private val project: Project) {
    companion object {
        const val LOMBOK = "1.18.30"
        const val SPRING_CLOUD = "2023.0.0"
        const val MAP_STRUCT = "1.5.5.Final"
        const val SPRING_DOC = "1.7.0"
        const val SWAGGER = "1.6.12"
        const val POSTGRES = "42.7.1"
        const val TEST_CONTAINERS = "1.19.3"
    }

    val SPRING_BOOT: String
        get() = project.property("springBootVersion").toString()
}
