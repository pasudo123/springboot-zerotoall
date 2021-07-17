package com.example.springbootjpabasis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
class SpringbootJpaBasisApplication

fun main(args: Array<String>) {
    runApplication<SpringbootJpaBasisApplication>(*args)
}
