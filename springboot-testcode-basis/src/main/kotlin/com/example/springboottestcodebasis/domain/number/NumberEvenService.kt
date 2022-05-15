package com.example.springboottestcodebasis.domain.number

import mu.KLogging
import org.springframework.stereotype.Service

@Service
class NumberEvenService {

    companion object: KLogging()

    fun doSomething(number: Long) {
        logger.info { "짝수 : $number" }
    }
}