package com.example.springbootbasis.api.scheduler

import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate

class SampleDynamicScheduleControllerTest {

    private val host = "http://localhost:8080/sample-scheduler"
    private val headers = mapOf(
        "Content-Type" to "application/json"
    )

    @Test
    fun `thread 를 n개 만들어서 동시에 수행시킵니다`() {

        val restTemplate = RestTemplate()

        (1..2).forEach {
            val map = mapOf<String, Any>("name" to "hello-$it")
            restTemplate.postForObject(host, map, Map::class.java)
        }
    }
}