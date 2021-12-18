plugins {
    kotlin("jvm")
}

val junitJupiterVersion = "5.4.2"
val kotestVersion = "4.6.1"
val mockkVersion = "1.12.0"

dependencies {

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // swagger
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")

    // basic
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // logging
    implementation("io.github.microutils:kotlin-logging:1.12.5")
}