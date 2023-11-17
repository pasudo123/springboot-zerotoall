// package com.example.springdocumenttraining.chapter01
//
// import org.springframework.beans.factory.FactoryBean
// import org.springframework.stereotype.Component
//
// @Component
// class CoffeeFactoryBean : FactoryBean<CoffeeController> {
//
//    private val id = "coffeeController"
//
//    override fun getObject(): CoffeeController {
//        return CoffeeController(id)
//    }
//
//    override fun getObjectType(): Class<*> {
//        return CoffeeController::class.java
//    }
//
//    override fun isSingleton(): Boolean {
//        return true
//    }
// }
