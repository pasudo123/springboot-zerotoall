package com.example.springbootjpabasis

import org.junit.jupiter.api.Test

class SequenceTester {

    @Test
    fun `Sequence usage`() {
        val current = sequenceOf("Hello", "World")
            .onEach { println(it) }

        println(">")
        current.toSet()
    }
}
