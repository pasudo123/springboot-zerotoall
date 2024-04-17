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
    kotlin("kapt") version kotlinVersion

    // kotlin lint
    id("org.jlleitschuh.gradle.ktlint") version klintVersion

    // https://github.com/grpc/grpc-kotlin/blob/master/compiler/README.md
    id("com.google.protobuf") version "0.9.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val kotestVersion: String = System.getProperty("version.kotestVersion")
val mockkVersion: String = System.getProperty("version.mocckVersion")
val springmockkVersion: String = System.getProperty("version.springmockkVersion")

dependencies {

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // aop
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // springboot3 에서 jakarta 필요.
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // grpc & protobuf
    // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-kotlin
    implementation("io.grpc:grpc-kotlin-stub:1.3.1")
    implementation("io.grpc:grpc-protobuf:1.62.2")
    implementation("com.google.protobuf:protobuf-kotlin:3.25.3")

    // mock & kotest
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("com.ninja-squad:springmockk:$springmockkVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

ktlint.filter {
    // generated 경로에 생성된 파일은 ktlint 검사 제외
    // https://github.com/JLLeitschuh/ktlint-gradle/issues/522#issuecomment-958756817
    exclude {
        val path = it.file.canonicalPath
        path.contains("generated")
    }
}

protobuf {
    protoc {
        println("### protoc=${libs.protoc.asProvider().get()}")
        artifact = libs.protoc.asProvider().get().toString()
        // 아래처럼 사용가능
        // artifact = "com.google.protobuf:protoc:3.25.3"
    }
    plugins {
        create("grpc") {
            println("### grpc create()=${libs.protoc.gen.grpc.java.get()}")
            artifact = libs.protoc.gen.grpc.java.get().toString()
            // 아래처럼 사용가능
            // artifact = "io.grpc:protoc-gen-grpc-java:1.62.2"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.1:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
                create("grpckt")
            }
            it.builtins {
                create("kotlin")
            }
        }
    }
}
