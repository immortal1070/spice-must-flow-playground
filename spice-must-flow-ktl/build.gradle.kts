import org.jetbrains.kotlin.builtins.StandardNames.FqNames.suppress
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    val kotlinPluginVersion = "1.7.22"

    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
//    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    kotlin("jvm") version kotlinPluginVersion
    kotlin("plugin.spring") version kotlinPluginVersion
    kotlin("plugin.jpa") version kotlinPluginVersion
    kotlin("plugin.allopen") version kotlinPluginVersion
//    kotlin("kapt") version "1.8.20"
}

group = "io.immortal"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

extra["testcontainersVersion"] = "1.17.4"
extra["openApiVersion"] = "1.6.14"
extra["eCacheVersion"] = "3.10.8"
extra["restAssuredVersion"] = "5.3.0"

dependencies {
    val querydslVersion = "5.0.0"
    val kotlinLoggingVersion = "3.0.5"
    val springDocOpenapiUiVersion = "1.7.0"
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-cache")
//    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-integration")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.zalando:problem-spring-web-starter:0.27.0")
//    implementation("org.zalando:jackson-datatype-problem:0.28.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework:spring-jdbc")
    implementation("org.springframework.integration:spring-integration-amqp")
//    implementation("org.springframework.integration:spring-integration-r2dbc")
//    implementation("org.springframework.integration:spring-integration-security")
    implementation("org.springframework.session:spring-session-core")
//    implementation("org.springdoc:springdoc-openapi-ui:${property("openApiVersion")}")
//    implementation("org.springdoc:springdoc-openapi-kotlin:${property("openApiVersion")}")
//    implementation("org.springdoc:springdoc-openapi-webmvc-core:${property("openApiVersion")}")
//    implementation("org.springdoc:springdoc-openapi-webflux-ui:${property("openApiVersion")}")
    implementation("org.hibernate.orm:hibernate-jcache")
    implementation("org.hibernate.orm:hibernate-core")
    implementation("org.ehcache:ehcache:${property("eCacheVersion")}")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
//    implementation("org.springdoc:springdoc-openapi-ui:$springDocOpenapiUiVersion")
//    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocOpenapiUiVersion")
//    implementation("org.springdoc:springdoc-openapi-webmvc-core:$springDocOpenapiUiVersion")
    // TODO check version
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
//    compileOnly("org.hibernate:hibernate-jpamodelgen")
    // TODO check version
//    compileOnly("org.hibernate:hibernate-jpamodelgen:6.1.7.Final")
//    annotationProcessor("org.hibernate:hibernate-jpamodelgen:6.1.7.Final")

//    kapt("org.hibernate:hibernate-jpamodelgen:6.1.7.Final")

//    implementation("javax.servlet:javax.servlet-api:3.1.0")
    //TODO implementation?
//    compile("com.querydsl:querydsl-core:$querydslVersion")
//    compile("com.querydsl:querydsl-sql:$querydslVersion")
//    compile("com.querydsl:querydsl-sql-codegen:$querydslVersion")
//    compile("com.querydsl:querydsl-sql-spring:$querydslVersion")
//    compile("com.querydsl:querydsl-jpa:$querydslVersion")

//    implementation("org.springdoc:springdoc-openapi-javadoc:${property("openApiVersion")}")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.postgresql:postgresql")
//    runtimeOnly("org.postgresql:r2dbc-postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
//    testImplementation("org.springframework.amqp:spring-rabbit-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("org.springframework.integration:spring-integration-test")
//    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("io.rest-assured:rest-assured:${property("restAssuredVersion")}")
    testImplementation("io.rest-assured:json-schema-validator:${property("restAssuredVersion")}")
    testImplementation("io.rest-assured:kotlin-extensions:${property("restAssuredVersion")}")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation(kotlin("test"))
    testImplementation("org.testcontainers:postgresql")
//    <jmeter.version>0.63</jmeter.version>
//
//    <dependency>
//    <groupId>us.abstracta.jmeter</groupId>
//    <artifactId>jmeter-java-dsl</artifactId>
//    <version>${jmeter.version}</version>
//    <scope>test</scope>
//    </dependency>
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
        jvmTarget = "17"
    }
}

val javaGeneratedSources = "src/generated/java"

//sourceSets.main.java.srcDirs += "${buildDir}/generated"
//
//compileJava {
//    options.annotationProcessorGeneratedSourcesDirectory = file("${buildDir}/generated")
//}
//tasks.withType<JavaCompile> {
////    options {
////
////    }
////    options.generatedSourceOutputDirectory = file(javaGeneratedSources)
//    options.annotationProcessorGeneratedSourcesDirectory = file(javaGeneratedSources)
//}
//sourceSets {
//    create("generated").java.srcDirs(javaGeneratedSources)
//}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    jvmArgs = listOf("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5006")
}

//tasks.test {
//    onlyIf("skipTests property is not set") {
//        !providers.gradleProperty("skipTests").isPresent()
//    }
//}
//tasks.test { onlyIf("mySkipTests property is not set") {
//    !project.hasProperty("skipTests")
//} }

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

//hibernate {
//    jpaMetamodel {
//        applyGeneratedAnnotation false
//        suppress 'raw'
//        generationOutputDirectory "${buildDir}/generated/sources/modelgen"
//        compileOutputDirectory "${buildDir}/classes/java/modelgen"
//    }
//}
//TODO check difference and newest approach
//tasks.test {
//    useJUnitPlatform()
//}