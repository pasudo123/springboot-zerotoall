package com.example.springbootconcurrencyv2basis.withredis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.connection.RedisConnection
import org.springframework.data.redis.core.RedisCallback
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SessionCallback
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class BagRedisRepository(
    @Qualifier("bagRedisLettuceTemplate")
    private val bagRedisLettuceTemplate: RedisTemplate<String, String>,
//    @Qualifier("bagRedisJedisTemplate")
//    private val bagRedisJedisTemplate: RedisTemplate<String, String>
) {

    fun getItem(id: Long): Long {
        val bagKey = "bag:$id"
        return bagRedisLettuceTemplate.opsForValue().get(bagKey)?.toLong() ?: 0L
    }

    fun addItemOrThrow(id: Long) {
        val bagKey = "bag:$id"
        val bagKeyByteArray = bagKey.toByteArray()

//        val results = bagRedisLettuceTemplate.execute { redisConn ->
//            try {
//                redisConn.watch(bagKeyByteArray)
//                val value = redisConn.get(bagKeyByteArray)
//                val assign = if (value == null) 0 else String(value).toInt()
//                val newValue = (assign + 1).toString()
//
//                redisConn.multi()
//                redisConn.set(bagKeyByteArray, newValue.toByteArray())
//                redisConn.exec()
//            } catch (exception: Exception) {
//                throw RuntimeException("아이템을 동시에 증가시킬 수 없습니다.")
//            } finally {
//                redisConn.close()
//            }
//        }

        val results = bagRedisLettuceTemplate.execute(object: SessionCallback<String> {
            @JvmName("executeByStringOperation")
            fun <K : String?, V : String?> execute(operations: RedisOperations<String, String>): String {

                val size = operations.opsForValue().get(bagKey)
                val newSize = if (size == null) 0 else size.toInt() + 1

                operations.watch(bagKey)
                operations.multi()
                operations.opsForValue().set(bagKey, newSize.toString())
                operations.exec()

                return "true"
            }

            @Suppress("UNCHECKED_CAST")
            override fun <K : Any?, V : Any?> execute(operations: RedisOperations<K, V>): String {
                return this.execute<String, String>(operations as RedisOperations<String, String>)
            }
        })
    }
}