package com.example.springbootbasis.practice.logger

class Launcher

data class Person(
    val name: String,
    val age: Int,
    var mind: String? = null
)

fun main() {
    val person = Person(name = "hong", age = 10)
    println(person)
    println(person.toBatMan())
}