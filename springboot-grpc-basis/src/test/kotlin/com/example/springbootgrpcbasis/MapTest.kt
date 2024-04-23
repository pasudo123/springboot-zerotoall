package com.example.springbootgrpcbasis

import org.junit.jupiter.api.Test

class MapTest {

    @Test
    fun `compute, put 메소드 확인`() {
        val group = mutableMapOf<String, MutableList<Int>>()

        val words = listOf("hello", "hello", "hi", "orange", "banana", "orange")

        words.forEach { word ->
            group.computeIfPresent(word) { _, value ->
                val nextNumber = value.size + 1
                value.add(nextNumber)
                value
            }

            group.putIfAbsent(word, mutableListOf(1))
        }

        println(group)
    }
}