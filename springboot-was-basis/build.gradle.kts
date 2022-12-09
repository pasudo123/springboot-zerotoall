import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = System.getProperty("version.kotlinVersion")
    val springBootVersion = System.getProperty("version.springBootVersion")
    val springBootManagementVersion = System.getProperty("version.springDependencyManagementVersion")
    val klintVersion = System.getProperty("version.ktlintVersion")

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springBootManagementVersion

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    // kotlin lint
    id("org.jlleitschuh.gradle.ktlint") version klintVersion
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/libs-snapshot")
}

val kotestVersion: String = System.getProperty("version.kotestVersion")
val mockkVersion: String = System.getProperty("version.mocckVersion")
val springmockkVersion: String = System.getProperty("version.springmockkVersion")

dependencies {
    // was undertow 이용
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow") {
        // UT026010: Buffer pool was not set on WebSocketDeploymentInfo, the default pool will be used 경고 메시지 제거
        exclude("io.undertow", "undertow-websockets-jsr")
    }

    // monitoring
    // implementation ("io.micrometer:micrometer-core:latest.integration")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
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
