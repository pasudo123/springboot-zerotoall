plugins {
    kotlin("jvm")
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

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

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

    // redis-reactive
    testImplementation("io.projectreactor:reactor-test:3.2.3.RELEASE")
}