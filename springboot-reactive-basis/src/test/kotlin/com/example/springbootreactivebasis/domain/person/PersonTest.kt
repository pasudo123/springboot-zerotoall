package com.example.springbootreactivebasis.domain.person

import org.junit.jupiter.api.Test

internal class PersonTest {

    @Test
    fun randomCreateTest() {
        val person = Person.random()

        println(person)
    }
}