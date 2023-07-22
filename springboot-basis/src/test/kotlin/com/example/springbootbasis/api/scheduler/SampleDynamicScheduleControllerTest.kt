package com.example.springbootbasis.api.scheduler

import khttp.post
import org.junit.jupiter.api.Test

class SampleDynamicScheduleControllerTest {

    private val host = "http://localhost:8080/sample-scheduler"
    private val headers = mapOf(
        "Content-Type" to "application/json"
    )

    @Test
    fun `thread 를 10개 만들어서 동시에 수행시킵니다`() {

        (1..15).forEach {
            val map = mapOf<String, Any>("name" to "hello-$it")
            post(host, headers = headers, json = map)
        }
    }
}