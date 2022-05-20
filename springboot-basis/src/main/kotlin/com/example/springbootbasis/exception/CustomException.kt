package com.example.springbootbasis.exception

open class CustomException(
    override val message: String
) : RuntimeException(message)

class AppleException(
    override val message: String
) : CustomException(message)

class BananaException(
    override val message: String
) : CustomException(message)