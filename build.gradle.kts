import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.noarg") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

group = "br.edu.faeterj.petropolis.tcc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven {
        url = uri("https://jaspersoft.jfrog.io/jaspersoft/third-party-ce-artifacts/")
    }
}

dependencies {
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.3")
    implementation("com.github.bohnman:squiggly-filter-jackson:1.3.18")
    implementation("com.nimbusds:oauth2-oidc-sdk:6.21.2")
    implementation("org.apache.commons:commons-lang3")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("org.springframework.security:spring-security-jwt:1.0.11.RELEASE")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.3.7.RELEASE")
    implementation("org.modelmapper:modelmapper:3.1.0")
    implementation("net.sf.jasperreports:jasperreports-functions:6.19.1")
    implementation("net.sf.jasperreports:jasperreports:6.19.1")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.189")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("mysql:mysql-connector-java")

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
    annotation("org.springframework.hateoas.server.core.Relation")
}

noArg {
    annotation("org.springframework.hateoas.server.core.Relation")
    annotation("br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg")
}

