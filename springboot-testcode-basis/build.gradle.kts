plugins {
    kotlin("jvm")
}

dependencies {
    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // logging
    implementation("io.github.microutils:kotlin-logging:1.12.5")

    // mock & kotest
    testImplementation("io.mockk:mockk:1.10.6")
    testImplementation("io.kotest:kotest-assertions-core:4.4.3")
}