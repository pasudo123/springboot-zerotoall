package com.example.springbootconcurrencyv2basis.withredis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.*
import javax.annotation.PostConstruct
import kotlin.random.Random

@Repository
class BagRedisRepository(
    @Qualifier("bagRedisLettuceTemplate")
    private val bagRedisLettuceTemplate: RedisTemplate<String, String>,
) {

    fun getItem(id: Long): Long {
        val bagKey = "bag:$id"
        return bagRedisLettuceTemplate.opsForValue().get(bagKey)?.toLong() ?: 0L
    }

    @PostConstruct
    fun init() {
        for (id in 0..10) {
            val bagKey = "bag:$id"
            bagRedisLettuceTemplate.unlink(bagKey)
            bagRedisLettuceTemplate.opsForValue().set(bagKey, "0")
        }
    }

    fun decrItem(id: Long) {
        val bagKey = "bag:$id"
        bagRedisLettuceTemplate.opsForValue().decrement(bagKey)
    }

    fun incrItemOrThrow(bag: Bag) {
        val bagKey = "bag:${bag.id!!}"

        val bagKeyByteArray = bagKey.toByteArray()

        bagRedisLettuceTemplate.execute { currentConn ->
            currentConn.use { redisConn ->

                redisConn.watch(bagKeyByteArray)

                // watch 이후에 조회되고 증감되어야 함
                val value = redisConn.get(bagKeyByteArray)
                val assign = if (value == null) 0 else String(value).split(":").first().toInt()
                val newSizeWithUUID = "${assign + 1}:${UUID.randomUUID().toString().substring(0, 5)}"

                redisConn.multi()
                redisConn.set(bagKeyByteArray, newSizeWithUUID.toByteArray())

                // When using WATCH, EXEC can return a Null reply if the execution was aborted.
                // https://redis.io/commands/exec/ 참고, null 이 반환되면 애플리케이션 단에서는 에러를 발생시킬 수 있도록 한다.
                // 완전한 동시성 처리가 안된다.?
                try {
                    val results = redisConn.exec()
                    println("Result :: $results")
                } catch (exception: Exception) {
                    // redisConn.exec() must not be null
                    throw RuntimeException("레디스에 클라이언트 동시 요청이 들어왔습니다. : ${exception.message}")
                }
            }
        }

//        bagRedisLettuceTemplate.execute(object: SessionCallback<String> {
//            @JvmName("executeByStringOperation")
//            fun <K : String?, V : String?> execute(operations: RedisOperations<String, String>): String {
//
//                // value 를 가변적으로 바꾸려고 한다면
//                val size = operations.opsForValue().get(bagKey)
//                val newSize = if (size == null) 1 else size.split(":").first().toInt() + 1
//                val newSizeWithUUID = "$newSize:${UUID.randomUUID().toString().substring(0, 5)}"
//
//                try {
//                    // 미리 세팅을 해놓음
//                    operations.watch(bagKey)
//
//                    operations.multi()
//                    operations.opsForValue().set(bagKey, newSizeWithUUID)
//                    operations.exec()
//                } catch (exception: Exception) {
//                    println("${exception.message}")
//                    throw RuntimeException("동시에 값을 바꾸려고 하네?!")
//                }
//
//                return "true"
//            }
//
//            @Suppress("UNCHECKED_CAST")
//            override fun <K : Any?, V : Any?> execute(operations: RedisOperations<K, V>): String {
//                return this.execute<String, String>(operations as RedisOperations<String, String>)
//            }
//        })
    }
}