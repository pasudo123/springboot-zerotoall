package com.example.springbootredisbasis

import org.springframework.stereotype.Component
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import javax.annotation.PreDestroy

@Component(value = "CustomRedisContainer")
@Testcontainers
class CustomRedisContainer {

    companion object {
        private const val REDIS_IMAGE_NAME = "redis:5.0.13"
        private const val REDIS_PORT = 6379

        @JvmStatic
        @Container
        var REDIS_CONTAINER = SpecifiedRedisContainer(DockerImageName.parse(REDIS_IMAGE_NAME)).apply {
            this.getMappedPort(16380)
            this.withExposedPorts(REDIS_PORT)
            this.start()
        }
    }

    @PreDestroy
    fun beforeDestroy() {
        REDIS_CONTAINER.stop()
    }

    // https://github.com/testcontainers/testcontainers-java/issues/318
    class SpecifiedRedisContainer(dockerImageName: DockerImageName) : GenericContainer<SpecifiedRedisContainer>(dockerImageName)
}