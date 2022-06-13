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
        val bagKey = "bag:$id"

        val bagKeyByteArray = bagKey.toByteArray()
        val results = bagRedisTemplate.execute ({ redisConn ->
            try {
                redisConn.watch(bagKeyByteArray)
                val value = redisConn.get(bagKeyByteArray)
                val assign = value?.toString()?.toInt() ?: 0

                redisConn.multi()
                redisConn.setNX(bagKeyByteArray, (assign + 1).toString().toByteArray())
                redisConn.exec()
            } catch (exception: Exception) {
                println("error occur : ${exception.message}")
            } finally {
                redisConn.close()
            }
        }, true)

//        val results = bagRedisTemplate.execute(object: SessionCallback<String> {
//            @JvmName("executeByStringOperation")
//            fun <K : String?, V : String?> execute(operations: RedisOperations<String, String>): String {
//
//                val size = bagRedisTemplate.opsForValue().get(bagKey)
//
//                operations.watch(bagKey)
//                operations.expire(bagKey, Duration.ofMinutes(5))
//
//                operations.multi()
//                operations.opsForValue().set(bagKey, "${(size?.toIntOrNull() ?: 0) + 1}")
//                val result = operations.exec()
//
//                println(result.toString())
//
//                return "true"
//            }
//
//            @Suppress("UNCHECKED_CAST")
//            override fun <K : Any?, V : Any?> execute(operations: RedisOperations<K, V>): String {
//                return this.execute<String, String>(operations as RedisOperations<String, String>)
//            }
//        })

        println("result : $results")
    }
}