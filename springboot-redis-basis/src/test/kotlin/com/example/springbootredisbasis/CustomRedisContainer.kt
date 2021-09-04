package com.example.springbootredisbasis

import org.springframework.stereotype.Component
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration
import java.time.temporal.ChronoUnit
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component(value = "CustomRedisContainer")
@Testcontainers
class CustomRedisContainer {

    @PostConstruct
    fun setup() {
        REDIS_CONTAINER = SpecifiedRedisContainer(REDIS_IMAGE_NAME).apply {
            this.withExposedPorts(REDIS_PORT)
            this.withStartupTimeout(Duration.of(20, ChronoUnit.SECONDS))
            this.start()
        }
    }

    companion object {
        private const val REDIS_IMAGE_NAME = "redis:5.0.13"
        private const val REDIS_PORT = 16390

        @JvmStatic
        @Container
        var REDIS_CONTAINER = SpecifiedRedisContainer(image = REDIS_IMAGE_NAME).apply {
            this.withExposedPorts(REDIS_PORT)
            this.withStartupTimeout(Duration.of(10, ChronoUnit.SECONDS))
            this.start()
        }
    }

    @PreDestroy
    fun beforeDestroy() {
        REDIS_CONTAINER.stop()
    }

    // https://github.com/testcontainers/testcontainers-java/issues/318
    class SpecifiedRedisContainer(image: String) : GenericContainer<SpecifiedRedisContainer>(image)
}