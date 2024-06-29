package com.example.springbootmongobasis.poc01.api

data class CafeMenuResponse(
    val cafeCount: Int,
    val coffeeCount: Int,
    val beverageCount: Int,
    val teeCount: Int
)