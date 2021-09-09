plugins {
    kotlin("jvm")
}

val retrofitVersion = "2.9.0"

dependencies {

    // jpa & mysql
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java")

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    // retrofit2 : for api communicate
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-jackson:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
    implementation("com.squareup.retrofit2:retrofit-mock:$retrofitVersion")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    // logging
    implementation("io.github.microutils:kotlin-logging:1.12.5")

    // swagger
    //https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui
    implementation ("io.springfox:springfox-swagger-ui:2.9.2")
    implementation ("io.springfox:springfox-swagger2:2.9.2")

    // querydsl : kapt 를 부모 gradle.build.kts 에 추가 (플러그인)
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    kapt("com.querydsl:querydsl-apt::jpa")
    implementation("com.querydsl:querydsl-jpa")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // redis-test
    testImplementation("it.ozimov:embedded-redis:0.7.3"){
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }

    // h2
    testImplementation("com.h2database:h2")

    // mock & kotest
    testImplementation("io.mockk:mockk:1.10.6")
    testImplementation("io.kotest:kotest-assertions-core:4.4.3")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
}

kotlin.sourceSets.main {
    println("kotlin sourceSets buildDir :: $buildDir")
    setBuildDir("$buildDir")
}