import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    val kotlinVersion = System.getProperty("version.kotlinVersion")
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
}

// all open 할 애노테이션 지정
allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {

    // querydsl : kapt 를 gradle.build.kts 에 추가
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    kapt("com.querydsl:querydsl-apt:4.4.0")
    implementation("com.querydsl:querydsl-jpa")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    runtimeOnly("mysql:mysql-connector-java")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // mock & kotest
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("io.kotest:kotest-assertions-core:5.0.3")
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
