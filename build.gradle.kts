import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.4.30"
    val kaptVersion = "1.5.20"
    val springVersion = "2.3.9.RELEASE"
    val springDependencyManagementVersion = "1.0.10.RELEASE"

    idea

    id("org.springframework.boot") version springVersion apply false
    id("io.spring.dependency-management") version springDependencyManagementVersion apply false

    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("kapt") version kaptVersion apply false

    // https://kotlinlang.org/docs/no-arg-plugin.html#jpa-support
    // kotlin jpa 사용 시, noargs 를 사용하기 위함 (하단에 apply kotlin-jpa 추가)
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
}

allprojects {
    group = "edu.pasudo123.kotlin"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {

    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-kapt")

    println("=======================================================================")
    println("Enabling Spring Boot plugin in project ${project.name}...")
    println("Enabling Spring Boot Dependency Management in project ${project.name}...")
    println("Enabling Kotlin Spring plugin in project ${project.name}...")

    tasks.withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            languageVersion = "1.4"
            apiVersion = "1.4"
            jvmTarget = "11"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
