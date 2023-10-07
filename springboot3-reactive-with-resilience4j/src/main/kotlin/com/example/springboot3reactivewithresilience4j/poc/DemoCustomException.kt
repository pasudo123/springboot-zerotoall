package com.example.springboot3reactivewithresilience4j.poc

sealed class DemoCustomException(
    message: String
) : RuntimeException(message)

class DemoRecordException(
    message: String
) : DemoCustomException(message)
