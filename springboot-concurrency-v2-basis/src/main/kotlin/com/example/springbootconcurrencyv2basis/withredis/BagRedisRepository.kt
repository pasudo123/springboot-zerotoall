package com.example.springbootconcurrencyv2basis.withredis

import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SessionCallback
import org.springframework.stereotype.Repository

@Repository
class BagRedisRepository(
    private val bagRedisTemplate: RedisTemplate<String, String>
) {

    fun addItemOrThrow(id: Long) {

        val results = bagRedisTemplate.execute(object: SessionCallback<List<Any>>{
            @JvmName("executeByStringOperation")
            fun <K : String?, V : String?> execute(operations: RedisOperations<String, String>): List<Any>? {
                val key = "bag:$id"
                // operations.watch(key)
                // operations.expire(key, Duration.ofMinutes(5))
                // operations.multi()

                operations.execute { conn ->
                    conn.watch(key.toByteArray())
                    conn.expire(key.toByteArray(), 300L)
                    conn.multi()
                    conn.incr(key.toByteArray())
                    conn.exec()
                }

                return null
            }

            @Suppress("UNCHECKED_CAST")
            override fun <K : Any?, V : Any?> execute(operations: RedisOperations<K, V>): List<Any>? {
                return this.execute<String, String>(operations as RedisOperations<String, String>)
            }
        })

        results?.forEachIndexed { index, result ->
            println("$index : $result")
        }

    }
}