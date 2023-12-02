package com.example.springdocumenttraining.chapter03

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class Chapter03Runner(
    private val applicationContext: ApplicationContext,
    private val coffeeService: CoffeeService
): ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        coffeeService.coffee
    }
}