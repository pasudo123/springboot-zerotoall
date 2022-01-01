import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea

    val kotlinVersion = "1.4.30"
    val kaptVersion = "1.5.20"
    val springVersion = "2.3.9.RELEASE"
    val springDependencyManagementVersion = "1.0.10.RELEASE"

    id("org.springframework.boot") version springVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("kapt") version kaptVersion

    // https://kotlinlang.org/docs/no-arg-plugin.html#jpa-support
    // kotlin jpa 사용 시, noargs 를 사용하기 위함 (하단에 apply kotlin-jpa 추가)
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    // https://kotlinlang.org/docs/all-open-plugin.html
    // kotlin jpa 사용 시, allopen final 키워드를 특정 애노테이션 기준으로 제거해주기 위함
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
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
