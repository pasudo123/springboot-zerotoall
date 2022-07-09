package com.example.springboottestcodebasis.archunit.layerd.controller

import com.example.springboottestcodebasis.archunit.layerd.service.CoffeeService

class CoffeeController(
    private val coffeeService: CoffeeService
) {
}