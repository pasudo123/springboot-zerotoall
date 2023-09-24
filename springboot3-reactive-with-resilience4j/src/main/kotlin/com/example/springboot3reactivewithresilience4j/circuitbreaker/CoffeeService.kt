package com.example.springboot3reactivewithresilience4j.circuitbreaker

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalTime

@Service
class CoffeeService(
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {

    private lateinit var circuitBreakerOfCoffeeService: io.github.resilience4j.circuitbreaker.CircuitBreaker

    companion object {
        const val COFFEE_SERVICE = "coffeeService"
        const val COFFEE_FALLBACK_METHOD = "coffeeFallbackMethod"
    }

    @PostConstruct
    fun init() {
        circuitBreakerOfCoffeeService = circuitBreakerRegistry.circuitBreaker(COFFEE_SERVICE)
    }

    @CircuitBreaker(name = COFFEE_SERVICE, fallbackMethod = COFFEE_FALLBACK_METHOD)
    fun ok(): Mono<CoffeeDto> {
        return Mono.just(CoffeeDto.of("OK", circuitBreakerOfCoffeeService))
    }

    @CircuitBreaker(name = COFFEE_SERVICE, fallbackMethod = COFFEE_FALLBACK_METHOD)
    fun error(): Mono<CoffeeDto> {
        return Mono.error(CoffeeRecordException("coffee-[RECORD]-exception, ${LocalTime.now()}"))
    }

    @CircuitBreaker(name = COFFEE_SERVICE, fallbackMethod = COFFEE_FALLBACK_METHOD)
    fun errorIgnore(): Mono<CoffeeDto> {
        return Mono.error(CoffeeIgnoreException("coffee-[IGNORE]-exception, ${LocalTime.now()}"))
    }

    @CircuitBreaker(name = COFFEE_SERVICE, fallbackMethod = COFFEE_FALLBACK_METHOD)
    fun slow(): Mono<CoffeeDto> {
        // 10초 대기
        Thread.sleep(10000L)
        return Mono.just(CoffeeDto.of("slow OK", circuitBreakerOfCoffeeService))
    }

    fun coffeeFallbackMethod(exception: Throwable): Mono<CoffeeDto> {
        val message = exception.message ?: "empty error, ${LocalTime.now()}"
        val coffeeDto = CoffeeDto.of("##fallback## $message", circuitBreakerOfCoffeeService)
        return Mono.just(coffeeDto)
    }
}
