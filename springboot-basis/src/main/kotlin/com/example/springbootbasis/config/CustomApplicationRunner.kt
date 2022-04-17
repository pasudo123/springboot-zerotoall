package com.example.springbootbasis.config

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class CustomApplicationRunner(
    private val context: ApplicationContext
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        println(context.getBean("appleKService"))
        println(context.getBean("bananaKService"))
    }
}