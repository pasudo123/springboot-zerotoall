package com.example.springboot3reactivewithresilience4j.timelimiter

sealed class SnackCustomException(
    message: String
) : RuntimeException(message)

class SnackDefaultException(
    message: String
) : SnackCustomException(message)
