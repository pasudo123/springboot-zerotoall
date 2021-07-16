package com.example.springbootjpabasis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages=["com.example.springbootjpabasis"])
class SpringbootJpaBasisApplication

fun main(args: Array<String>) {
    runApplication<SpringbootJpaBasisApplication>(*args)
}
