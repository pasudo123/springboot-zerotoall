package com.example.springdocumenttraining.chapter01

class SampleOfDi

fun main() {
    val myController = MyController()
    myController.index()
}

interface MyService {
    fun task()
}

class MyCoffeeService : MyService {
    override fun task() {}
}

class MySnackService : MyService  {
    override fun task() {}
}

class MyController{

    private val myService: MyService = MyCoffeeService()

    fun index() {
        myService.task()
    }
}