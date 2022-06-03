package com.example.springbootjvmbasis.domain.coffee

import org.springframework.stereotype.Service

@Service
class CoffeeService {

    fun addCoffees(size: Int): List<Coffee> {
        val coffees = mutableListOf<Coffee>()

        repeat(size) {
            coffees.add(Coffee())
        }

        return coffees
    }

    fun addCoffeesComplex(size: Int): List<Coffee> {
        val coffees = mutableListOf<Coffee>()

        repeat(size) {
            coffees.add(Coffee())
        }

        return coffees.map { coffee ->
            Coffee("complex-${coffee.name}")
        }
    }
}