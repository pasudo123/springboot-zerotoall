package com.example.springbootredisbasis.config.domain.snack

import com.example.springbootredisbasis.config.toJson
import com.example.springbootredisbasis.config.toObject
import com.example.springbootredisbasis.repository.RedisDefaultRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class SnackRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
): RedisDefaultRepository {

    private fun generateKey(id: Long): String {
        return "snack:$id"
    }

    fun save(snack: Snack, redisTtl: RedisDefaultRepository.RedisTTL): Boolean? {
        val key = generateKey(snack.id)

        redisTemplate.expire(key, redisTtl.long, redisTtl.unit)
        return redisTemplate
            .opsForValue()
            .setIfAbsent(key, snack.toJson())
    }

    fun findOne(id: Long): Snack? {
        val key = generateKey(id)

        val stringValue = redisTemplate
            .opsForValue()
            .get(key)

        return stringValue?.toObject()
    }
}