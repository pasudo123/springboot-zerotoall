package com.example.springbootjvmbasis.domain.coffee

import java.util.UUID

data class Coffee(
    val name: String = "아이스 아메리카노",
    val id: String = UUID.randomUUID().toString()
)