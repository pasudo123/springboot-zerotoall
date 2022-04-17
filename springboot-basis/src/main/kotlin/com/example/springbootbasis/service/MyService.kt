package com.example.springbootbasis.service

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Scope
import javax.annotation.PostConstruct

@Scope("prototype")
class MyService {

    private val log = LoggerFactory.getLogger(javaClass)
    private lateinit var name: String

    @PostConstruct
    fun postConstruct() {
        log.info("postConstruct ... :: $name")
    }

    fun doSomething() {
        log.info("do something... :: $name")
    }

    fun setName(name: String) {
        this.name = name
    }
}