package com.example.springbootreactivebasis.domain.person

import java.util.UUID
import kotlin.random.Random

data class Person(
    val id: String,
    val name: String,
    val age: Int
) {

    companion object {
        fun random(): Person {
            return Person(
                id = UUID.randomUUID().toString(),
                name = "홍길동-${Random.nextLong(1000000000L)}",
                age = Random.nextInt(100)
            )
        }
    }
}
