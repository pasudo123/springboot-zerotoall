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

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

val kotestVersion: String = System.getProperty("version.kotestVersion")
val mockkVersion: String = System.getProperty("version.mocckVersion")

dependencies {

    // implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // retrofit2 : for api communicate
    // https://square.github.io/retrofit/
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    // okhttp3 logging
    implementation("com.squareup.okhttp3:logging-interceptor")

    // logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")

    // MockWebServer external api test
    // https://github.com/square/okhttp/blob/master/mockwebserver/README.md
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/mockwebserver
    // retrofit2 의 okhttp 버전과 맞춰주어야 한다.
    testImplementation("com.squareup.okhttp3:mockwebserver:3.14.9") {
        // junit4 를 제외해주면,  org.junit.rules.ExternalResource 에 대한 의존성이 없어 컴파일 에러가 난다.
        // this.exclude(group = "junit", module = "junit")
    }

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

    // https://mockk.io/ & https://kotest.io/
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("io.kotest:kotest-assertions-core:5.1.0")
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

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {

}
