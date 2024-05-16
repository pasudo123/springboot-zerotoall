import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = System.getProperty("version.kotlinVersion")
    val springBootVersion = System.getProperty("version.springBootVersion")
    val springBootManagementVersion = System.getProperty("version.springDependencyManagementVersion")
    val ktlintVersion = System.getProperty("version.ktlintVersion")

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springBootManagementVersion

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    // https://kotlinlang.org/docs/no-arg-plugin.html#jpa-support
    // kotlin jpa 사용 시, noargs 를 사용하기 위함 : 프록시 생성 시 필요.
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    // https://kotlinlang.org/docs/all-open-plugin.html
    // kotlin jpa 사용 시, allopen final 키워드를 특정 애노테이션 기준으로 제거해주기 위함
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion

    // kotlin lint
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

val kotestVersion: String = System.getProperty("version.kotestVersion")
val mockkVersion: String = System.getProperty("version.mocckVersion")
val springmockkVersion: String = System.getProperty("version.springmockkVersion")
val queryDslVersion: String = System.getProperty("version.queryDslVersion")

kotlin {
    this.sourceSets {
        // querydsl QClass 생성
        println("@@ kotlin sourceSets buildDir :: $buildDir")
        setBuildDir("$buildDir")
    }
}

dependencies {

    // jpa & mysql
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.32")

    // jpa envers
    // https://mvnrepository.com/artifact/org.springframework.data/spring-data-envers
    implementation("org.springframework.data:spring-data-envers:3.1.7")

    // querydsl : kapt 를 gradle.build.kts 에 추가
    // springboot 3.x 부터 javax -> jakarta 로 넘어감
    // https://mvnrepository.com/artifact/com.querydsl/querydsl-jpa
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // swagger : springboot3.x 에서 이것만 있어도 됨
    // https://springdoc.org/#Introduction
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // h2
    testImplementation("com.h2database:h2")

    // logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")

    // springboot starter-test
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // mock & kotest
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("com.ninja-squad:springmockk:$springmockkVersion")

    testImplementation("mysql:mysql-connector-java:8.0.32")
}

tasks.withType<KotlinCompile> {
    this.kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
