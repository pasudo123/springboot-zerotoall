plugins {
    kotlin("jvm")
}

dependencies {

    // jpa & mysql
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java")

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // swagger
    //https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui
    implementation ("io.springfox:springfox-swagger-ui:2.9.2")
    implementation ("io.springfox:springfox-swagger2:2.9.2")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}