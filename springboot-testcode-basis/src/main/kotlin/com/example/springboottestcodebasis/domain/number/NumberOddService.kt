package com.example.springboottestcodebasis.domain.number

import mu.KLogging
import org.springframework.stereotype.Service

@Service
class NumberOddService {

    companion object: KLogging()

    fun doSomething(number: Long) {
        logger.info { "홀수 : $number" }
    }
}