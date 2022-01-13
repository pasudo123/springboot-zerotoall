package com.example.mockk.model

data class Car(
    val name: String
)

class CarMaker {

    fun makeCars(names: List<String>): List<Car> {
        return names.map { Car("고급 $it") }
    }
}