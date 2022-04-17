package com.example.springbootbasis.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MyDetailService {

    private val log = LoggerFactory.getLogger(javaClass)

    fun process(name: String) {
        log.info("process $name ~~")
    }
}