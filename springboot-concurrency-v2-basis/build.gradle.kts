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
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    // https://kotlinlang.org/docs/no-arg-plugin.html#jpa-support
    // kotlin jpa 사용 시, noargs 를 사용하기 위함 : 프록시 생성 시 필요.
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    // https://kotlinlang.org/docs/all-open-plugin.html
    // kotlin jpa 사용 시, allopen final 키워드를 특정 애노테이션 기준으로 제거해주기 위함
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion

    // kotlin lint
    id("org.jlleitschuh.gradle.ktlint") version klintVersion
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

kotlin.sourceSets.main {
    println("kotlin sourceSets buildDir :: $buildDir")
    // querydsl QClass 생성
    setBuildDir("$buildDir")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    jcenter()
}

val retrofitVersion = "2.9.0"

val kotestVersion: String = System.getProperty("version.kotestVersion")
val mockkVersion: String = System.getProperty("version.mocckVersion")
val springmockkVersion: String = System.getProperty("version.springmockkVersion")
val queryDslVersion: String = System.getProperty("version.queryDslVersion")

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

    // redis + lettuce
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // spring-data-redis 이랑 버전호환을 맞추어야 함
    // redis jedis client + apache commons pool : jedis pool config 에서 아파치 풀 필요
    // https://mvnrepository.com/artifact/org.apache.commons/commons-pool2
    implementation("redis.clients:jedis:3.8.0")
    implementation("org.apache.commons:commons-pool2:2.11.1")

    // implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    // logging
    implementation("io.github.microutils:kotlin-logging:1.12.5")

    // swagger
    // https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    implementation("io.springfox:springfox-swagger2:2.9.2")

    // querydsl : kapt 를 gradle.build.kts 에 추가
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jpa")
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // khttp
    implementation("khttp:khttp:0.1.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // redis-test
    testImplementation("it.ozimov:embedded-redis:0.7.3") {
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }

    // h2
    testImplementation("com.h2database:h2")

    // mock & kotest
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("com.ninja-squad:springmockk:$springmockkVersion")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
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
