package com.example.springboot3kafkabasis.model

import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random

data class Coffee(
    val key: String,
    val name: String,
    val price: Long,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    companion object {
        fun create(): Coffee {
            val uuid = UUID.randomUUID().toString()
            val key = Random.nextLong(Int.MAX_VALUE.toLong())
            return Coffee(
                key = uuid,
                name = "아메리카노-$key",
                price = Random.nextLong(10000, 1000001) / 10
            )
        }
    }
}