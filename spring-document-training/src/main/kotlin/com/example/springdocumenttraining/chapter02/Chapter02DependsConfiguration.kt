package com.example.springdocumenttraining.chapter02

import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn

@Configuration
class Chapter02DependsConfiguration {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    @DependsOn(value = ["motherBean"])
    fun fatherBean(): FatherBean {
        return FatherBean(log)
    }

    @Bean
    fun motherBean(): MotherBean {
        return MotherBean(log)
    }

    class MotherBean(
        private val log: Logger
    ) {
        @PostConstruct
        fun init() {
            // log.info("mother bean init")
        }
    }

    class FatherBean(
        private val log: Logger
    ) {
        @PostConstruct
        fun init() {
            // log.info("father bean init")
        }
    }
}
