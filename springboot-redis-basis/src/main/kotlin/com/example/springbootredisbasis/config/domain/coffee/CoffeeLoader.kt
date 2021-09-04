package com.example.springbootredisbasis.config.domain.coffee

import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component

@Component
class CoffeeLoader(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, Coffee>
) {


}