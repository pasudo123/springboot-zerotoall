import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.12"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // https://github.com/lukas-krecan/ShedLock
    // https://mvnrepository.com/artifact/net.javacrumbs.shedlock/shedlock-spring
    // https://mvnrepository.com/artifact/net.javacrumbs.shedlock/shedlock-provider-redis-spring
    // redis lock
    implementation("net.javacrumbs.shedlock:shedlock-spring:4.34.0")
    implementation("net.javacrumbs.shedlock:shedlock-provider-redis-spring:4.34.0")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // aop
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
