package com.example.springboot3reactivewithresilience4j.circuitbreaker

sealed class CoffeeCustomException(
    message: String
) : RuntimeException(message)

/**
 * circuit 실패율 처리 시 무시하는 익셉션
 */
class CoffeeIgnoreException(
    message: String
) : CoffeeCustomException(message)

/**
 * circuit 실패율 처리 시 기록하는 익셉션
 */
class CoffeeRecordException(
    message: String
) : CoffeeCustomException(message)
