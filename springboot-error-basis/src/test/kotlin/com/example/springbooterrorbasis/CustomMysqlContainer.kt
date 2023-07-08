package com.example.springbooterrorbasis

import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.annotation.PreDestroy

@Component(value = "CustomMySqlContainer")
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
            .apply { withCommand("--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci") }
            .apply { withUrlParam("useTimezone", "true") }
            .apply { withUrlParam("serverTimezone", "Asia/Seoul") }
            .apply { start() }
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
