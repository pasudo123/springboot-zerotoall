import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    val kotlinVersion = System.getProperty("version.kotlinVersion")
    val ktlintVersion = System.getProperty("version.ktlintVersion")
    val springBootVersion = System.getProperty("version.springBootVersion")
    val springBootManagementVersion = System.getProperty("version.springDependencyManagementVersion")

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
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // swagger
    // https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    implementation("io.springfox:springfox-swagger2:2.9.2")

    // mongo
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    // querydsl
    kapt("com.querydsl:querydsl-jpa:4.4.0")
    implementation("com.querydsl:querydsl-jpa:4.4.0")
    implementation("com.querydsl:querydsl-apt:4.4.0")

    // mongo reactive
    // implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    // logging
    implementation("io.github.microutils:kotlin-logging:1.12.5")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:junit-jupiter:1.15.3")

    // mock & kotest
    testImplementation("io.mockk:mockk:1.10.6")
    testImplementation("io.kotest:kotest-assertions-core:4.4.3")

    // test : mongodb Test Container : https://mvnrepository.com/artifact/org.testcontainers/mongodb
    testImplementation("org.testcontainers:mongodb:1.15.3")
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

allOpen {
    this.annotation("org.springframework.data.mongodb.core.mapping.Document")
}

kapt {
    annotationProcessors("org.springframework.data.mongodb.repository.support.MongoAnnotationProcessor")
}
