package com.example.springboot3reactivewithresilience4j.circuitbreaker

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("coffee")
class CoffeeController(
    private val coffeeService: CoffeeService
) {

    @GetMapping("ok")
    fun ok(): Mono<CoffeeDto> {
        return coffeeService.ok()
    }

    @GetMapping("error-record")
    fun error(): Mono<CoffeeDto> {
        return coffeeService.error()
    }

    @GetMapping("error-ignore")
    fun errorIgnore(): Mono<CoffeeDto> {
        return coffeeService.errorIgnore()
    }

    @GetMapping("slow")
    fun slow(): Mono<CoffeeDto> {
        return coffeeService.slow()
    }
}