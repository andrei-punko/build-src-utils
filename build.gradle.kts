plugins {
    `kotlin-dsl`
    jacoco
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api("org.springframework.boot:spring-boot-gradle-plugin:2.4.13")
}
