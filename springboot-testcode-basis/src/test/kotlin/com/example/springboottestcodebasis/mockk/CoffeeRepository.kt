package com.example.springboottestcodebasis.mockk

class CoffeeRepository {

    fun findAllByPrice(price: Long): List<Coffee> {
        return emptyList()
    }
}