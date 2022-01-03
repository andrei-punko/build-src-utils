plugins {
    id("org.gradle.kotlin.kotlin-dsl") version "2.1.4"
    jacoco
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api("org.springframework.boot:spring-boot-gradle-plugin:2.4.3")
}
