package com.example.springbooterrorbasis

import com.example.springbooterrorbasis.CustomMysqlContainer.Companion.MYSQL_CONTAINER
import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(
    classes = [SpringbootErrorBasisApplication::class],
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