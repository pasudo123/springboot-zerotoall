package com.example.springboottestcodebasis.archunit.util

import com.example.springboottestcodebasis.archunit.layerd.repository.CoffeeRepository
import com.example.springboottestcodebasis.archunit.layerd.service.CoffeeService
import java.util.UUID

object AppUtil {

    fun doReturnString(): String {
        return UUID.randomUUID().toString()
    }

//    // util -> layerd.service 에 의존
//    fun doReturnStringUseService(): String {
//        return CoffeeService(CoffeeRepository()).makeCoffee()
//    }
}