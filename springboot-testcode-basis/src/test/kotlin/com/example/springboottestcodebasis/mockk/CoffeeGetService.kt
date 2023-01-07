package com.example.springboottestcodebasis.mockk

class CoffeeGetService(
    private val coffeeRepository: CoffeeRepository
) {

    fun getCoffeesByPrices(prices: List<Long>): List<Coffee> {
        return prices.map {
            coffeeRepository.findAllByPrice(it)
        }
            .flatten()
            .distinctBy { it }
    }
}