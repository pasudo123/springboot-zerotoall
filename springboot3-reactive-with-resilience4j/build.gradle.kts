import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"

    // kotlin lint
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2022.0.4"
extra["resilience4jVersion"] = "2.0.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // netty : io.netty:netty-resolver-dns-native-macos 추가
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.97.Final:osx-aarch_64")

    // reactive redis
    // implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    // reactive 관련
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // resilience4j
    implementation("io.github.resilience4j:resilience4j-spring-boot3:${property("resilience4jVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // resilience4j reactor : reactive 처리 시 필요.
    implementation("io.github.resilience4j:resilience4j-reactor:${property("resilience4jVersion")}")

    // implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")
    // developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    // prometheus
    implementation("io.micrometer:micrometer-registry-prometheus")

    // reactor test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

    // https://mockk.io/ & https://kotest.io/
    testImplementation("io.mockk:mockk:1.12.8")
    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
