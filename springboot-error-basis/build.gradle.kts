plugins {
    kotlin("jvm")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

kotlin.sourceSets.main {
    println("kotlin sourceSets buildDir :: $buildDir")
    setBuildDir("$buildDir")
}

val junitJupiterVersion = "5.4.2"
val kotestVersion = "4.6.1"
val mockkVersion = "1.12.0"

dependencies {

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // basic
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // jpa & mysql
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java")

    // querydsl : kapt 를 부모 gradle.build.kts 에 추가 (플러그인)
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    kapt("com.querydsl:querydsl-apt::jpa")
    implementation("com.querydsl:querydsl-jpa")

    // logging
    implementation("io.github.microutils:kotlin-logging:1.12.5")

    // mock & kotest
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")

    // testcontainer
    testImplementation("org.testcontainers:testcontainers:1.16.0")
    testImplementation("org.testcontainers:junit-jupiter:1.16.0")

    // testcontainer : mysql, https://www.testcontainers.org/modules/databases/mysql/
    testImplementation("org.testcontainers:mysql:1.16.0")

    // h2
    testImplementation("com.h2database:h2")
}