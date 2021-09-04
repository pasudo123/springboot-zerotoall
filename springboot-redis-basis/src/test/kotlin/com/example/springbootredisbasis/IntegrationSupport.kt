package com.example.springbootredisbasis

import com.example.springbootredisbasis.CustomRedisContainer.Companion.REDIS_CONTAINER
import io.kotest.matchers.shouldNotBe
import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(
    classes = [SpringbootRedisBasisApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Import(value = [IntergrationTestConfiguration::class])
class IntegrationSupport() {

    private val logger = KotlinLogging.logger {}

    @Test
    @DisplayName("redis container 는 정상적으로 올라온다.")
    fun redisContainerCheckTest() {
        REDIS_CONTAINER shouldNotBe null
        logger.info { "redis host : ${REDIS_CONTAINER.testHostIpAddress}" }
        logger.info { "redis host : ${REDIS_CONTAINER.host}" }
        logger.info { "redis port : ${REDIS_CONTAINER.firstMappedPort}" }
    }
}