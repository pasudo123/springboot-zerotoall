package com.example.springbootwasbasis.coffee

import java.time.LocalDateTime

data class Coffee(
    val id: String,
    val name: String,
    val date: LocalDateTime
)