plugins {
    `kotlin-dsl`
    jacoco
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api("org.springframework.boot:spring-boot-gradle-plugin:2.7.18")
}
