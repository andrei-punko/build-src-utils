package by.andd3dfx.build

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.exclude

/**
 * When library version not populated - version from Spring Boot parent pom will be used
 */

const val implementation = "implementation"
const val testImplementation = "testImplementation"
const val compileOnly = "compileOnly"
const val annotationProcessor = "annotationProcessor"
const val compileClasspath = "compileClasspath"
const val testCompileOnly = "testCompileOnly"
const val testAnnotationProcessor = "testAnnotationProcessor"

abstract class Starters(
    val dependencyScope: DependencyHandlerScope,
    private val starterGroup: String
) {
    val allStarters: MutableMap<ExternalModuleDependency, Sequence<ExternalModuleDependency.() -> Unit>> =
        mutableMapOf()

    inline fun <reified T : Starters> init(body: T.() -> Unit) {
        (this as T).body()
        allStarters.forEach {
            it.value.forEach { action ->
                it.key {
                    action()
                }
            }
        }
    }

    operator fun String.invoke(starterName: String): ExternalModuleDependency {
        val conf = this
        var dependency: ExternalModuleDependency? = null
        dependencyScope {
            conf("${starterGroup}:${starterName}") {
                dependency = this
            }
            allStarters.putIfAbsent(dependency!!, sequenceOf())
        }
        return dependency!!
    }

    operator fun ExternalModuleDependency.invoke(body: ExternalModuleDependency.() -> Unit) {
        allStarters[this]?.plus(body)
    }

    fun applyToAll(body: ExternalModuleDependency.() -> Unit) {
        allStarters.forEach { it.value.plus(body) }
    }
}

class SpringBootStarters(
    val dependencyHandlerScope: DependencyHandlerScope,
    private val dependencyVersions: DependencyVersions
) : Starters(dependencyHandlerScope, "org.springframework.boot") {

    fun springBoot() = implementation("org.springframework.boot:spring-boot-starter")

    fun webFlux() = implementation("spring-boot-starter-webflux")

    fun thymeleaf() = implementation("spring-boot-starter-thymeleaf")

    fun web(configurationName: String = implementation) = configurationName("spring-boot-starter-web")

    fun dataJpa() = implementation("spring-boot-starter-data-jpa")

    fun actuator() = implementation("spring-boot-starter-actuator")

    fun validation() = implementation("spring-boot-starter-validation")

    fun security() = implementation("spring-boot-starter-security")

    fun test() = testImplementation("spring-boot-starter-test")

    fun configurationProcessor() = annotationProcessor("spring-boot-configuration-processor")

    fun ws(): Dependency {
        val webServicesImplementation = implementation("spring-boot-starter-web-services")
        dependencyHandlerScope {
            implementation("wsdl4j:wsdl4j")
        }
        return webServicesImplementation
    }

    fun cloud(version: String = DependencyVersions.SPRING_CLOUD, body: SpringCloudStarters.() -> Unit) {
        dependencyHandlerScope {
            implementation(platform("org.springframework.cloud:spring-cloud-dependencies:${version}"))
        }
        SpringCloudStarters(dependencyHandlerScope).init(body)
    }
}

class SpringCloudStarters(
    dependencyHandlerScope: DependencyHandlerScope
) : Starters(dependencyHandlerScope, "org.springframework.cloud") {

    fun gateway() = implementation("spring-cloud-starter-gateway")
    fun config() = implementation("spring-cloud-starter-config")
    fun configServer() = implementation("spring-cloud-config-server")
    fun kafkaBus() = implementation("spring-cloud-starter-bus-kafka")
}

fun DependencyHandlerScope.springBoot(
    project: org.gradle.api.Project,
    body: SpringBootStarters.() -> Unit = {}
) {
    val dependencyVersions = DependencyVersions(project)
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${dependencyVersions.SPRING_BOOT}"))
    SpringBootStarters(this, dependencyVersions).init(body)
}

fun DependencyHandlerScope.lombok(version: String = DependencyVersions.LOMBOK) {
    compileOnly("org.projectlombok:lombok:${version}")
    annotationProcessor("org.projectlombok:lombok:${version}")
    testCompileOnly("org.projectlombok:lombok:${version}")
    testAnnotationProcessor("org.projectlombok:lombok:${version}")
}

fun DependencyHandlerScope.apacheCommonsLang() {
    implementation("org.apache.commons:commons-lang3")
}

fun DependencyHandlerScope.mapstruct(version: String = DependencyVersions.MAP_STRUCT) {
    implementation("org.mapstruct:mapstruct:${version}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${version}")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:${version}")
}

fun DependencyHandlerScope.springDoc(version: String = DependencyVersions.SPRING_DOC) {
    implementation("org.springdoc:springdoc-openapi-ui:${version}")
}

fun DependencyHandlerScope.swagger(version: String = DependencyVersions.SWAGGER) {
    implementation("io.swagger:swagger-annotations:${version}")
    implementation("io.swagger:swagger-models:${version}") {
        exclude("org.slf4j", "slf4j-api")
    }
}

fun DependencyHandlerScope.validation() {
    implementation("jakarta.validation:jakarta.validation-api")
}

fun DependencyHandlerScope.postgres(version: String = DependencyVersions.POSTGRES) {
    implementation("org.postgresql:postgresql:${version}")
}

fun DependencyHandlerScope.h2() {
    implementation("com.h2database:h2")
}

fun DependencyHandlerScope.flywayCore() {
    implementation("org.flywaydb:flyway-core")
}

fun DependencyHandlerScope.testContainers(version: String = DependencyVersions.TEST_CONTAINERS) {
    testImplementation("org.testcontainers:testcontainers:${version}")
    testImplementation("org.testcontainers:junit-jupiter:${version}")
}

fun DependencyHandlerScope.postgresTestContainers(version: String = DependencyVersions.TEST_CONTAINERS) {
    testImplementation("org.testcontainers:postgresql:${version}")
}
