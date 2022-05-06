package com.example.springbootredisbasis.config.domain.snack

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class SnackRedisRepository(
    private val redisTemplate: RedisTemplate<String, Any>
) {


}