package com.example.springboottestcodebasis.archunit.layerd.service

import com.example.springboottestcodebasis.archunit.layerd.repository.CoffeeRepository

class CoffeeService(
    private val coffeeRepository: CoffeeRepository
) {

    fun makeCoffee(): String {
        return "making coffee..."
    }
}