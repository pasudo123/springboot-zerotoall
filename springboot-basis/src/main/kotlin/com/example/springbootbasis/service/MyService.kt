package com.example.springbootbasis.service

import org.slf4j.LoggerFactory
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

    fun doSomething() {
        log.info("do something... :: $name")
        myDetailService.process(name)
    }

    fun setName(name: String) {
        this.name = name
    }
}