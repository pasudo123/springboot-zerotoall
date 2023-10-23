package com.example.springboot3kafkabasis.exception

sealed class CustomException(
    message: String
): RuntimeException(message)

class CoffeeRecordException(
    override val message: String
): CustomException(message)

class CoffeeIgnoreException(
    override val message: String
): CustomException(message)