package com.example.springbootbasis.config

import com.example.springbootbasis.service.MyService
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
        println(context.getBean("appleKService"))
        println(context.getBean("appleKService"))
        println(context.getBean("bananaKService"))
        println(context.getBean("bananaKService"))
        println(context.getBean("bananaKService"))

        val appleService = context.getBean("appleKService") as MyService
        appleService.doSomething()

        val bananaService = context.getBean("bananaKService") as MyService
        bananaService.doSomething()
    }
}