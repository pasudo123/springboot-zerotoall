package com.example.springbootbasis.service

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime
import javax.annotation.PostConstruct

class MyService(
    private val myDetailService: MyDetailService
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private lateinit var name: String

    @PostConstruct
    fun postConstruct() {
        log.info("postConstruct ... :: $name")
    }

    @Scheduled(initialDelay = 3000, fixedDelay = 3000)
    fun task() {
        log.info("task... :: $name :: ${LocalDateTime.now()}")
        myDetailService.process(name)
    }

    fun setName(name: String) {
        this.name = name
    }
}