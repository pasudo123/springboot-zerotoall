plugins {
    application
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.allopen") version "1.9.20"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

dependencies {

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation(kotlin("reflect"))

    // benchmark
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.10")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    this.jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}

/**
 * https://github.com/Kotlin/kotlinx-benchmark/blob/master/docs/configuration-options.md
 */
benchmark {
    targets {
        register("main")
        register("jvm")
    }
}