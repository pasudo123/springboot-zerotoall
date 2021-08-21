package com.example.springboottestcontainerbasis

import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
@Testcontainers
class CustomMysqlContainer {

    private val logger = KotlinLogging.logger {}

    companion object {
        @JvmStatic
        @Container
        val MYSQL_CONTAINER = SpecifiedMySQLContainer(image = "mysql:8.0.26")
            .apply { withDatabaseName("testdb") }
            .apply { withUsername("testroot") }
            .apply { withPassword("testrootpass") }
            .apply { start() }
    }

    @PostConstruct
    fun init() {
        MYSQL_CONTAINER.isRunning shouldBe true
        logger.info { "======== [started] mysql container ========" }
        logger.info { "======== imageName : ${MYSQL_CONTAINER.dockerImageName}" }
        logger.info { "======== databaseName : ${MYSQL_CONTAINER.databaseName}" }
        logger.info { "======== username : ${MYSQL_CONTAINER.username}" }
        logger.info { "======== password : ${MYSQL_CONTAINER.password}" }
        logger.info { "======== exposedPorts : ${MYSQL_CONTAINER.exposedPorts}" }
        logger.info { "======== boundPortNumbers : ${MYSQL_CONTAINER.boundPortNumbers}" }
        logger.info { "==========================================" }
    }

    @PreDestroy
    fun beforeDestroy() {
        MYSQL_CONTAINER.stop()
        logger.info { "======== [stopped] mysql container ========" }
        logger.info { "===========================================" }
    }

    // https://github.com/testcontainers/testcontainers-java/issues/318
    class SpecifiedMySQLContainer(image: String) : MySQLContainer<SpecifiedMySQLContainer>(image)
}