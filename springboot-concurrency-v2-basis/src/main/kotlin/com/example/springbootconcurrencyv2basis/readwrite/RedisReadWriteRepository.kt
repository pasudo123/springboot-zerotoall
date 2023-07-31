package com.example.springbootconcurrencyv2basis.readwrite

import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisReadWriteRepository(
    private val stringRedisTemplate: StringRedisTemplate
) {

    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun save(uuid: String, isInit: Boolean = true) {
        log.info("save $uuid")

        if (isInit) {
            stringRedisTemplate.opsForValue().set(uuid, uuid)
        } else {
            stringRedisTemplate.opsForValue().setIfPresent(uuid, uuid)
        }
    }

    suspend fun unlink(uuid: String) {
        log.info("unlink $uuid")
        stringRedisTemplate.unlink(uuid)
    }
}