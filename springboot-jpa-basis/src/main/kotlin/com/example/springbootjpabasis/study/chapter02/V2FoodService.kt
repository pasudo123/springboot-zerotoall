package com.example.springbootjpabasis.study.chapter02

import com.example.springbootjpabasis.study.Food
import com.example.springbootjpabasis.study.FoodDto
import com.example.springbootjpabasis.study.FoodRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.random.Random

@Service
class V2FoodService(
    private val foodRepository: FoodRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun create(dto: FoodDto): Long {
        val suffix = UUID.randomUUID().toString().substring(IntRange(0, 5))
        val food = Food(name = "${dto.name}:$suffix", subName = dto.subName)
        foodRepository.save(food)

        val foodId = food.id

        log.info("food.id=${foodId}")

        if (Random.nextBoolean()) {
            throw RuntimeException("error")
        }

        return foodId!!
    }
}