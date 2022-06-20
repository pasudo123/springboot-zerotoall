package com.example.springbootconcurrencyv2basis.withredis

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class BagControllerTest {

    private val itemHost = "http://localhost:18801/bags"

    @Test
    @DisplayName("redis 이용 : BagController 로 동시에 요청을 보낸다. (Watch 이용)")
    fun concurrencyToBagWithRedisTestWatch() {

        // given
        val bag = khttp.post(itemHost)
        val id = 1
        println("bag id : $id")

        // when
        runBlocking(Dispatchers.IO) {
            (1..4).map {
                async {
                    khttp.post("$itemHost/$id/with-watch")
                }
            }.awaitAll()
        }

        val response = khttp.get("$itemHost/$id")
        println(response.jsonObject.toString())
    }

    @Test
    @DisplayName("redis 이용 : BagController 로 동시에 요청을 보낸다.")
    fun concurrencyToBagWithRedisTest() {

        // given
        val bag = khttp.post(itemHost)
        val id = 1
        println("bag id : $id")

        // when
        runBlocking(Dispatchers.IO) {
            (1..6).map {
                async {
                    khttp.post("$itemHost/$id")
                }
            }.awaitAll()
        }

        val response = khttp.get("$itemHost/$id")
        println(response.jsonObject.toString())
    }
}