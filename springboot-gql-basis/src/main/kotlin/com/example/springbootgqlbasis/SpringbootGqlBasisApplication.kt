package com.example.springbootgqlbasis

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.DependsOn

@SpringBootApplication
class SpringbootGqlBasisApplication {

    @Bean
    @DependsOn(value = ["Initializer"])
    fun init(
        initializer: Initializer
    ) = CommandLineRunner {
        initializer.save()
    }
}

fun main(args: Array<String>) {
    runApplication<SpringbootGqlBasisApplication>(*args)
}
