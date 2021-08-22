package com.example.springboottestcontainerbasis

import io.mockk.InternalPlatformDsl.toArray
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.annotation.PostConstruct
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
            // .testcontainers.properties 에 `testcontainers.reuse.enable=true` 로 설정해주어야 한다. 그리고 아직 알파단계이다.
//            .apply { withReuse(true) }
            .apply { start() }
    }

    @PostConstruct
    fun init() {
        logger.info { "======== [started] mysql container ========" }
        logger.info { "======== imageName : ${MYSQL_CONTAINER.dockerImageName}" }
        logger.info { "======== databaseName : ${MYSQL_CONTAINER.databaseName}" }
        logger.info { "======== username : ${MYSQL_CONTAINER.username}" }
        logger.info { "======== password : ${MYSQL_CONTAINER.password}" }
        logger.info { "======== jdbcUrl : ${MYSQL_CONTAINER.jdbcUrl}" }
        logger.info { "======== exposedPorts : ${MYSQL_CONTAINER.exposedPorts}" }
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