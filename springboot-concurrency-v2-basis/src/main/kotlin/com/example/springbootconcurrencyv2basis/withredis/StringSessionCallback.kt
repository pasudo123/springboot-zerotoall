package com.example.springbootconcurrencyv2basis.withredis

import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.SessionCallback

interface StringSessionCallback : SessionCallback<List<Any>> {

    fun <K : String?, V : String?> execute(operations: StringRedisOperation): List<Any>?

    override fun <K : Any?, V : Any?> execute(operations: RedisOperations<K, V>): List<Any>? {
        return emptyList()
    }
}
