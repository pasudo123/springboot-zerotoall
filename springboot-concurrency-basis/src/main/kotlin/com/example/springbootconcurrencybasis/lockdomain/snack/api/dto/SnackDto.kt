package com.example.springbootconcurrencybasis.lockdomain.snack.api.dto

class SnackDto {

    data class CreateRequest(
        val name: String,
        val price: Long
    )
}