package com.example.springbootjpabasis.study01

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class FoodInitializer(
    private val foodRepository: FoodRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun init(dto: FoodDto): Food {
        val food = Food(name = dto.name, subName = dto.subName)

        return foodRepository.save(food)
    }

    @Transactional
    fun doSomething(food: Food) {
        log.info("food=$food")
    }
}
