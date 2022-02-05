import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = System.getProperty("version.kotlin")
    val springBootVersion = System.getProperty("version.springboot")
    val springBootManagementVersion = System.getProperty("version.springbootManagement")
    val default = System.getProperty("version.default")
    default.toString()

    val versions = StringBuilder()
    versions.appendLine("=======================")
    versions.appendLine("kotlin = $kotlinVersion")
    versions.appendLine("springboot = $springBootVersion")
    versions.appendLine("springboot-management = $springBootManagementVersion")
    versions.appendLine("default = $default")
    versions.appendLine("=======================")
    println(versions.toString())

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springBootManagementVersion
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {

    // @ConfigurationProperties 사용
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // quartz 에서 사용하기 위함
    // https://mvnrepository.com/artifact/com.zaxxer/HikariCP
    // java version 에 따라서 HikariCP 버전을 맞게 써주어야 함
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("com.zaxxer:HikariCP:5.0.1")


    // implementation("org.springframework.boot:spring-boot-starter-jdbc")

    runtimeOnly("mysql:mysql-connector-java")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
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
