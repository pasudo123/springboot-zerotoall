package com.example.springbootredisbasis

import com.example.springbootredisbasis.CustomRedisContainer.Companion.REDIS_CONTAINER
import io.kotest.matchers.shouldNotBe
import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@TestEnvironment
@SpringBootTest(
    classes = [SpringbootRedisBasisApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
class IntegrationSupport {

    private val logger = KotlinLogging.logger {}

    @Test
    @DisplayName("redis container 는 정상적으로 올라온다.")
    fun redisContainerCheckTest() {
        REDIS_CONTAINER shouldNotBe null
        logger.info { "redis host : ${REDIS_CONTAINER.host}" }
        logger.info { "redis firstMappedPort : ${REDIS_CONTAINER.firstMappedPort}" }
        logger.info { "redis exposedPorts : ${REDIS_CONTAINER.exposedPorts}" }
        logger.info { "redis portBindings : ${REDIS_CONTAINER.portBindings}" }
    }
}
