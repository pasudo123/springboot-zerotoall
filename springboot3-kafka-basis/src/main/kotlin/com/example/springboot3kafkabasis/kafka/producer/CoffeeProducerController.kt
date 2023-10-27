package com.example.springboot3kafkabasis.kafka.producer

import com.example.springboot3kafkabasis.model.Coffee
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coffees")
class CoffeeProducerController(
    private val coffeeProducer: CoffeeProducer
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun produceCoffee(
        @RequestParam size: Int = 0
    ) {
        repeat(size) {
            runCatching {
                coffeeProducer.produceCoffee(Coffee.create())
            }.onFailure {
                val stackTrace = it.stackTraceToString()
                log.error("produceCoffee exception : ${it.message}, stacktrace=$stackTrace")
            }
        }
    }
}
