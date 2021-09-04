package com.example.springbootredisbasis.config.domain.coffee

import java.util.*

data class Coffee(
    val id: String = generateId(),
    val name: String
) {

    companion object {
        fun generateId(): String {
            return UUID.randomUUID()
                .toString()
                .replace("-", "")
        }
    }
}