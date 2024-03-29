package com.example.springbootredisbasis.domain.coffee

import java.util.UUID

data class Coffee(
    val name: String
) {

    val id: String = generateId()

    companion object {
        fun generateId(): String {
            return UUID.randomUUID()
                .toString()
                .replace("-", "")
        }
    }
}
