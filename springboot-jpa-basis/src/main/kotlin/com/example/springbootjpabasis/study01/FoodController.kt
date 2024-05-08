package com.example.springbootjpabasis.study01

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("v1/foods")
@RestController
class FoodController(
    private val foodService: FoodService,
) {

    @PostMapping
    fun create(
        @RequestBody dto: FoodDto
    ): Long {
        return foodService.create(dto)
    }
}