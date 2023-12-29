package by.andd3dfx.build

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType

fun Project.api(
    group: String = "by.andd3dfx",
    version: String,
    name: String,
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

    the<SourceSetContainer>().apply {
        getByName("main").java.srcDir(file("${project.layout.buildDirectory}/swagger-code-$name/src/main/java"))
    }

    tasks.withType<JavaCompile> {
        dependsOn(
            tasks.named("generateSwaggerCode")
        )
    }

    jacoco()

    this.body()
}
