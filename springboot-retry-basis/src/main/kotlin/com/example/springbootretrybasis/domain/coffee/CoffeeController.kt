package com.example.springbootretrybasis.domain.coffee

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("coffee")
class CoffeeController(
    private val coffeeService: CoffeeService
) {

    @PostMapping("no-recover")
    fun addCoffee(): String {
        coffeeService.insertCoffeeNoRecover("no-recover-call")
        return "ok"
    }

    @PostMapping("with-recover")
    fun addCoffeeWithRecover(): String {
        coffeeService.insertCoffeeWithRecover("with-recover-call", "sample")
        return "ok"
    }

    @PostMapping("with-template")
    fun addCoffeeWithTemplate(): String {
        coffeeService.insertCoffeeWithRetryTemplate()
        return "ok"
    }
}