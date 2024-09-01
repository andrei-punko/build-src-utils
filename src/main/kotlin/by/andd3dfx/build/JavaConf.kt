package by.andd3dfx.build

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

fun Project.javaProject(javaVersion: JavaVersion = JavaVersion.VERSION_21) {
    plugins.apply("org.gradle.java")
    configure<JavaPluginExtension> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion

        project.tasks.withType(JavaCompile::class.java).forEach { task ->
            task.options.encoding = "UTF-8"
        }
    }
}

fun Project.javaCompilerArgs(vararg args: String) {
    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(args)
    }
}
