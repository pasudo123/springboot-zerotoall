package com.example.springboot3reactivewithresilience4j.timelimiter

import io.github.resilience4j.timelimiter.annotation.TimeLimiter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.LocalTime

@Service
class SnackService {

    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        const val SNACK_SERVICE = "snackService"
        const val SNACK_FALLBACK_METHOD = "snackFallbackMethod"
    }

    @TimeLimiter(name = SNACK_SERVICE, fallbackMethod = SNACK_FALLBACK_METHOD)
    fun ok(): Mono<SnackDto> {
        return Mono.just(SnackDto.of("OK"))
    }

    @TimeLimiter(name = SNACK_SERVICE, fallbackMethod = SNACK_FALLBACK_METHOD)
    fun timeout(second: Long): Mono<SnackDto> {
        // 특정 메소드의 실행시간을 관여하기 위해선 timeLimiter 가 대안이 될 수 있음
        return Mono.just(SnackDto.of("ok and delay=$second"))
            .delayElement(Duration.ofSeconds(second))
    }

    @TimeLimiter(name = SNACK_SERVICE, fallbackMethod = SNACK_FALLBACK_METHOD)
    fun error(): Mono<SnackDto> {
        // error 가 발생하여도, Fallback method 가 동작함
        return Mono.error(SnackDefaultException("snack-[DEFAULT]-exception, ${LocalTime.now()}"))
    }

    fun snackFallbackMethod(exception: Throwable): Mono<SnackDto> {
        log.error("snackFallbackMethod with @TimeLimiter, ${LocalTime.now()}")
        val message = exception.message ?: "empty error, ${LocalTime.now()}"
        val snackDto = SnackDto.of("##fallback## $message")
        return Mono.just(snackDto)
    }
}
