package com.example.springboot3jobrunnerbasis.task

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class ShortTermTask {

    private val log = LoggerFactory.getLogger(javaClass)

    fun task(param: String? = null) {
        log.info("### short-term-task... ${param?.let { "param=${param}" }}::${LocalTime.now()}")
    }
}