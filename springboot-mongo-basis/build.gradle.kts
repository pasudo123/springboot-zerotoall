plugins {
    kotlin("jvm")
}

dependencies {

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // swagger
    //https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui
    implementation ("io.springfox:springfox-swagger-ui:2.9.2")
    implementation ("io.springfox:springfox-swagger2:2.9.2")

    // mongo
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    // logging
    implementation("io.github.microutils:kotlin-logging:1.12.5")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")
}