package com.example.springboot3reactivewithresilience4j

import io.github.resilience4j.circuitbreaker.CircuitBreaker

data class Metrics(
    val circuitState: String,
    val failureRate: String,
    val numberOfFailedCalls: String,
    val slowCallRate: String,
    val numberOfSlowCalls: String,
    val numberOfSuccessCalls: String,
    val numberOfNotPermittedCalls: String,
    val numberOfBufferedCalls: String
) {
    companion object {
        fun of(circuitBreaker: CircuitBreaker? = null): Metrics? {
            if (circuitBreaker == null) {
                return null
            }
            
            return Metrics (
                circuitState = circuitBreaker.state.toString(),
                failureRate = "${circuitBreaker.metrics.failureRate}%",
                numberOfFailedCalls = "${circuitBreaker.metrics.numberOfFailedCalls}",
                slowCallRate = "${circuitBreaker.metrics.slowCallRate}%",
                numberOfSlowCalls = "${circuitBreaker.metrics.numberOfSlowCalls}",
                numberOfSuccessCalls = "${circuitBreaker.metrics.numberOfSuccessfulCalls}",
                numberOfNotPermittedCalls = "${circuitBreaker.metrics.numberOfNotPermittedCalls}",
                numberOfBufferedCalls = "${circuitBreaker.metrics.numberOfBufferedCalls}"
            )
        }
    }
}
