package com.example.springbootwasbasis.coffee

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import java.time.Duration

class CoffeeControllerTest {

    private val restTemplate = RestTemplateBuilder()
        .setReadTimeout(Duration.ofSeconds(5))
        .setConnectTimeout(Duration.ofSeconds(5))
        .setBufferRequestBody(true)
        .build()
    private val local = "http://localhost:8080"

    init {
        // 로그를 출력하지 않기 위함
        val loggers = listOf("org.apache.http", "org.springframework.web")
        loggers.forEach { logger ->
            (LoggerFactory.getLogger(logger) as Logger).apply {
                this.level = Level.INFO
                this.isAdditive = false
            }
        }
    }

    @Test
    fun `restTemplate 확인`() {
        val response = restTemplate.getForEntity("$local/coffees", String::class.java)
        response.body?.let { println(it) }
    }
}