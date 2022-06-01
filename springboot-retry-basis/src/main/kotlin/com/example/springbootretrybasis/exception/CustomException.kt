package com.example.springbootretrybasis.exception

open class CustomException(
    message: String
): RuntimeException(message)

class CoffeeInsertException(
    override val message: String
): CustomException(message)