package com.example.springboot3jobrunnerbasis.task

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class LongTermTask {

    private val log = LoggerFactory.getLogger(javaClass)

    fun task() {
        Thread.sleep(10000L)
        log.info("### long-term-task... ${LocalTime.now()}")
    }
}