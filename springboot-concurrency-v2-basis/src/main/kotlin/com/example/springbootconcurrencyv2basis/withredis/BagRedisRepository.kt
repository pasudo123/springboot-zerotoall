package com.example.springbootconcurrencyv2basis.withredis

import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SessionCallback
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class BagRedisRepository(
    private val bagRedisTemplate: RedisTemplate<String, String>
) {

    fun addItemOrThrow(id: Long) {
        val results = bagRedisTemplate.execute(object: SessionCallback<String>{
            @JvmName("executeByStringOperation")
            fun <K : String?, V : String?> execute(operations: RedisOperations<String, String>): String {
                val bagKey = "bag:$id"
                val callKey = "bag:$id:call"

                operations.watch(callKey)
                operations.expire(callKey, Duration.ofMinutes(5))

                operations.watch(bagKey)
                operations.expire(bagKey, Duration.ofMinutes(5))

                // 1개인경우 에러가 발생하여야 하는데, 에러가 발생하지 않는다. ?
                operations.multi()

                val size = operations.opsForValue().get(bagKey) ?: "0"
                operations.opsForValue().set(bagKey, "${size.toInt() + 1}")

                val bool = operations.opsForValue().get(callKey)?.toBoolean() ?: false
                operations.opsForValue().set(callKey, "${!bool}")

                operations.exec()
                return "true"
            }

            @Suppress("UNCHECKED_CAST")
            override fun <K : Any?, V : Any?> execute(operations: RedisOperations<K, V>): String? {
                return this.execute<String, String>(operations as RedisOperations<String, String>)
            }
        })

        println("result : $results")
//        results?.forEachIndexed { index, result ->
//            println("$index : $result")
//        }
    }
}