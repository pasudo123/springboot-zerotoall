package com.example.springboottestcontainerbasis

import com.example.springboottestcontainerbasis.CustomMysqlContainer.Companion.MYSQL_CONTAINER
import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.MySQLContainer

@ActiveProfiles("test")
@SpringBootTest(
    classes = [SpringbootTestcontainerBasisApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Import(value = [IntergrationTestConfiguration::class])
class IntergrationSupport {

    private val logger = KotlinLogging.logger {}

    @Test
    @DisplayName("MySQL Container 는 동작중이다.")
    fun initTest() {
        MYSQL_CONTAINER.isRunning shouldBe true
    }
}