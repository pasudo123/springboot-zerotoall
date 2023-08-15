package com.example.springbootredisbasis.domain.snack

import com.example.springbootredisbasis.config.toJson
import com.example.springbootredisbasis.config.toObject
import com.example.springbootredisbasis.repository.RedisDefaultRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.stereotype.Repository

@Repository
class SnackRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
): RedisDefaultRepository {

    private fun generateKey(id: Long): String {
        return "$REDIS_KEY:$id"
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

    fun findKeys(): List<String> {

        val keys = mutableListOf<String>()

        val scanOptions = ScanOptions.scanOptions()
            .match("$REDIS_KEY:*")
            .count(5)
            .build()

        redisTemplate.execute { conn ->
            val cursor = conn.scan(scanOptions)

            while(cursor.hasNext()) {
                val next = cursor.next()
                val currentKey = String(next, Charsets.UTF_8)
                keys.add(currentKey)
            }
        }

        return keys
    }

    fun flush() {
        redisTemplate.connectionFactory?.connection?.flushAll()
    }

    companion object {
        private const val REDIS_KEY = "snack"
    }
}