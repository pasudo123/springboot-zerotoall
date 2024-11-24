package com.example.springbootjpabasis.study.chapter02

import com.example.springbootjpabasis.study.FoodDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/v2/foods")
@RestController
class V2FoodController(
    private val foodService: V2FoodService
) {

    @PostMapping
    fun create(@RequestBody dto: FoodDto): Long {
        return foodService.create(dto)
    }
}