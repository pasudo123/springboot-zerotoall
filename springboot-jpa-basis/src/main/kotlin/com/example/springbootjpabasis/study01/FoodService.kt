package com.example.springbootjpabasis.study01

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FoodService(
    private val foodRepository: FoodRepository,
    private val foodInitializer: FoodInitializer
) {

    @Transactional
    fun create(dto: FoodDto): Long {
        val food = foodInitializer.init(dto)

        val changedFood = food.apply { this.toGood() }
        foodRepository.saveAndFlush(food)

        return changedFood.id!!
    }

    fun findAll(): List<Food> {
        return foodRepository.findAll()
    }
}
