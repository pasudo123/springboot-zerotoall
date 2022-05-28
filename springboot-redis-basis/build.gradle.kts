plugins {
    val kotlinVersion = System.getProperty("version.kotlinVersion")
    val springBootVersion = System.getProperty("version.springBootVersion")
    val springBootManagementVersion = System.getProperty("version.springDependencyManagementVersion")
    val klintVersion = System.getProperty("version.ktlintVersion")

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springBootManagementVersion

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    // kotlin lint
    id("org.jlleitschuh.gradle.ktlint") version klintVersion
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

val kotestVersion: String = System.getProperty("version.kotestVersion")
val mockkVersion: String = System.getProperty("version.mocckVersion")
val springmockkVersion: String = System.getProperty("version.springmockkVersion")

dependencies {

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // basic
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    // logging
    implementation("io.github.microutils:kotlin-logging:1.12.5")

    // mock & kotest & springboot-test
    testImplementation("io.mockk:mockk:${mockkVersion}")
    testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
    testImplementation("io.kotest:kotest-property:${kotestVersion}")
    testImplementation("com.ninja-squad:springmockk:${springmockkVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        this.exclude(group ="org.junit.vintage", module = "junit-vintage-engine")
    }

    // testcontainer : mysql, https://www.testcontainers.org/modules/databases/mysql/
    // @TestContainer 및 @Container 를 사용할 수 있도록 해줌
    testImplementation("org.testcontainers:testcontainers:1.16.3")
    testImplementation("org.testcontainers:junit-jupiter:1.16.3")

    // redis-reactive
    testImplementation("io.projectreactor:reactor-test:3.2.3.RELEASE")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}