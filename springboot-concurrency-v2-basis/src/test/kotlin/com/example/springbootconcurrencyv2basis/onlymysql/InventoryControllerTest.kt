package com.example.springbootconcurrencyv2basis.onlymysql

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis


internal class InventoryControllerTest {

    private val inventoryV1Host = "http://localhost:18801/inventory/v1"
    private val inventoryV2Host = "http://localhost:18801/inventory/v2"
    private val inventoryV3Host = "http://localhost:18801/inventory/v3"

    @Test
    @DisplayName("일반 : InventoryController 로 동시에 요청을 보낸다")
    fun concurrenyToInventoryControllerV1Test() {

        val inventory = khttp.post(inventoryV1Host)
        val id = inventory.jsonObject["id"]
        println("inventory id : $id")

        val stopWatch = measureTimeMillis {
            runBlocking(Dispatchers.IO) {
                // inventory 의 size 보다 더 큰 값을 요청
                (1..10).map {
                    async {
                        khttp.post("$inventoryV1Host/$id")
                    }
                }.awaitAll()
            }
        }

        val response = khttp.get("$inventoryV1Host/$id")
        println("${stopWatch}ms ----------------------------")
        println(response.jsonObject.toString())
    }

    @Test
    @DisplayName("낙관적락(옵티미스틱락) : InventoryController 로 동시에 요청을 보낸다")
    fun concurrenyToInventoryControllerV2Test() {

        val inventory = khttp.post(inventoryV2Host)
        val id = inventory.jsonObject["id"]
        println("inventory id : $id")

        val stopWatch = measureTimeMillis {
            runBlocking(Dispatchers.IO) {
                // inventory 의 size 보다 더 큰 값을 요청
                (1..10).map {
                    async {
                        khttp.post("$inventoryV2Host/$id")
                    }
                }.awaitAll()
            }
        }

        val response = khttp.get("$inventoryV2Host/$id")
        println("${stopWatch}ms ----------------------------")
        println(response.jsonObject.toString())
    }

    @Test
    @DisplayName("비관적락(페시미스틱락) : PESSIMISTIC_READ")
    fun concurrenyToInventoryControllerV3Test() {

        val inventory = khttp.post(inventoryV3Host)
        val id = inventory.jsonObject["id"]
        println("inventory id : $id")

        val stopWatch = measureTimeMillis {
            runBlocking(Dispatchers.IO) {
                // inventory 의 size 보다 더 큰 값을 요청
                (1..10).map {
                    async {
                        khttp.post("$inventoryV3Host/$id/ps-read")
                    }
                }.awaitAll()
            }
        }

        val response = khttp.get("$inventoryV3Host/$id")
        println("${stopWatch}ms ----------------------------")
        println(response.jsonObject.toString())
    }

    @Test
    @DisplayName("비관적락(페시미스틱락) : PESSIMISTIC_WRITE")
    fun concurrenyToInventoryControllerV4Test() {

        val inventory = khttp.post(inventoryV3Host)
        val id = inventory.jsonObject["id"]
        println("inventory id : $id")

        val stopWatch = measureTimeMillis {
            runBlocking(Dispatchers.IO) {
                // inventory 의 size 보다 더 큰 값을 요청
                (1..10).map {
                    async {
                        khttp.post("$inventoryV3Host/$id/ps-write")
                    }
                }.awaitAll()
            }
        }

        val response = khttp.get("$inventoryV3Host/$id")
        println("${stopWatch}ms ----------------------------")
        println(response.jsonObject.toString())
    }
}