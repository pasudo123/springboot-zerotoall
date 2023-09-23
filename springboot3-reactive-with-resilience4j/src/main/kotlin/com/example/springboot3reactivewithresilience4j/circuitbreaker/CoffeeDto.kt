package com.example.springboot3reactivewithresilience4j.circuitbreaker

import io.github.resilience4j.circuitbreaker.CircuitBreaker

data class CoffeeDto(
    val message: String,
    val state: CircuitStateWithMetrics
) {
    data class CircuitStateWithMetrics(
        val circuitState: String,
        val failureRate: String,
        val numberOfFailedCalls: String,
        val slowCallRate: String,
        val numberOfSlowCalls: String,
        val numberOfSuccessCalls: String,
        val numberOfNotPermittedCalls: String,
        val numberOfBufferedCalls: String,
    )

    companion object {
        fun of(message: String, circuitBreaker: CircuitBreaker): CoffeeDto {
            return CoffeeDto(
                message = message,
                state = CircuitStateWithMetrics(
                    circuitState = circuitBreaker.state.toString(),
                    failureRate = "${circuitBreaker.metrics.failureRate}%",
                    numberOfFailedCalls = "${circuitBreaker.metrics.numberOfFailedCalls}",
                    slowCallRate = "${circuitBreaker.metrics.slowCallRate}%",
                    numberOfSlowCalls = "${circuitBreaker.metrics.numberOfSlowCalls}",
                    numberOfSuccessCalls = "${circuitBreaker.metrics.numberOfSuccessfulCalls}",
                    numberOfNotPermittedCalls = "${circuitBreaker.metrics.numberOfNotPermittedCalls}",
                    numberOfBufferedCalls = "${circuitBreaker.metrics.numberOfBufferedCalls}"
                )
            )
        }
    }
}