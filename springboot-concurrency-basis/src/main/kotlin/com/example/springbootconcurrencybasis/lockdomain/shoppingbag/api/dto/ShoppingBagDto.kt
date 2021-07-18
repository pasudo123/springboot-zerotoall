package com.example.springbootconcurrencybasis.lockdomain.shoppingbag.api.dto

class ShoppingBagDto {

    data class CreateRequest(
        val name: String,
        val holdCount: Long
    )
}