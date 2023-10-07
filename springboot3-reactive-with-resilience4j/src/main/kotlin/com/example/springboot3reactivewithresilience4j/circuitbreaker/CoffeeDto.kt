package com.example.springboot3reactivewithresilience4j.circuitbreaker

import com.example.springboot3reactivewithresilience4j.Metrics
import io.github.resilience4j.circuitbreaker.CircuitBreaker

data class CoffeeDto(
    val message: String,
    val metrics: Metrics? = null
) {
    companion object {
        fun of(message: String, circuitBreaker: CircuitBreaker): CoffeeDto {
            return CoffeeDto(
                message = message,
                metrics = Metrics.of(circuitBreaker)
            )
        }
    }
}
