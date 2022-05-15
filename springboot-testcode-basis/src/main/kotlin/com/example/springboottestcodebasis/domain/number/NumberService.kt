package com.example.springboottestcodebasis.domain.number

import org.springframework.stereotype.Service

@Service
class NumberService(
    private val numberEvenService: NumberEvenService,
    private val numberOddService: NumberOddService
) {

    fun process(number: Long) {

        if (number % 2 == 0L) {
            numberEvenService.doSomething(number)
            return
        }

        numberOddService.doSomething(number)
    }
}