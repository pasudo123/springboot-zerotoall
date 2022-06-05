package com.example.springbootjvmbasis.domain.coffee

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("coffee")
class CoffeeController(
    private val coffeeService: CoffeeService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun addCoffees(
        @RequestParam("size") size: Int
    ): String {
        log.info("POST /coffee?size=$size")
        val coffees = coffeeService.addCoffees(size)
        return "커피 ${coffees.size}잔 완성!"
    }

    @PostMapping("complex")
    fun addCoffeeWithComplex(
        @RequestParam("size") size: Int
    ): List<Coffee> {
        log.info("POST /coffee/complex?size=$size")
        return coffeeService.addCoffeesComplex(size)
    }

    @GetMapping("memory")
    fun getCoffeeAppMemoryStatistics(): String {

        log.info("GET /coffee/memory")

        return """
            {
                "heapSize": ${Runtime.getRuntime().totalMemory() / 1000000}MB,
                "heapMaxSize": ${Runtime.getRuntime().maxMemory() / 1000000}MB,
                "heapFreeSize": ${Runtime.getRuntime().freeMemory() / 1000000}MB
            }
        """.trimIndent()
    }
}