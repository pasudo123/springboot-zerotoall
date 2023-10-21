package com.example.springboot3kafkabasis.model

import java.time.LocalDateTime
import kotlin.random.Random

data class Coffee(
    val name: String,
    val price: Long,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    companion object {
        fun create(): Coffee {
            return Coffee(
                name = "아메리카노-${String(Random.nextBytes(15))}",
                price = Random.nextLong(10000, 1000001) % 10
            )
        }
    }
}