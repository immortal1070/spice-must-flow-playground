import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "io.immortal"
version = "0.0.1-SNAPSHOT"

plugins {
    val kotlinPluginVersion = "1.7.22"

    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version kotlinPluginVersion
    kotlin("plugin.spring") version kotlinPluginVersion
    kotlin("plugin.jpa") version kotlinPluginVersion
    kotlin("plugin.allopen") version kotlinPluginVersion
}

repositories {
    mavenCentral()
}

extra["testcontainersVersion"] = "1.17.4"
val debugPort = "5006"

dependencies {
    val kotlinLoggingVersion = "3.0.5"
    val eCacheVersion = "3.10.8"
    val restAssuredVersion = "5.3.0"
    val openApiStarterVersion = "2.1.0"
    val problemsHandlerVersion = "0.29.1"

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-integration")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework:spring-jdbc")
    implementation("org.springframework.integration:spring-integration-amqp")
    implementation("org.springframework.session:spring-session-core")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiStarterVersion")
    implementation("org.zalando:problem-spring-web-starter:$problemsHandlerVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.liquibase:liquibase-core")
    implementation("org.hibernate.orm:hibernate-jcache")
    implementation("org.hibernate.orm:hibernate-core")
    implementation("org.ehcache:ehcache:$eCacheVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("org.springframework.integration:spring-integration-test")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:json-schema-validator:$restAssuredVersion")
    testImplementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")

    //TODO to be added later
//    runtimeOnly("org.postgresql:r2dbc-postgresql")
//    testImplementation("org.springframework.amqp:spring-rabbit-test")
//    testImplementation("org.springframework.security:spring-security-test")
//    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
//    implementation("org.springframework.boot:spring-boot-starter-security")
//    implementation("org.springframework.integration:spring-integration-r2dbc")
//    implementation("org.springframework.integration:spring-integration-security")
//    testImplementation("org.testcontainers:r2dbc")
//    testImplementation("org.testcontainers:rabbitmq")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

val javaGeneratedSources = "src/generated/java"

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    jvmArgs = listOf("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:$debugPort")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

kotlin {
    jvmToolchain(17)
}