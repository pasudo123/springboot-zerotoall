package com.example.springbootbasis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SpringbootBasisApplication

fun main(args: Array<String>) {
    runApplication<SpringbootBasisApplication>(*args)
}
