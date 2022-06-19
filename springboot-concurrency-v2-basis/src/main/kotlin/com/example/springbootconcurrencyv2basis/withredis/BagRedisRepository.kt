package com.example.springbootconcurrencyv2basis.withredis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct

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

                redisConn.multi()
                // (1) 아래의 방식은 완전한 동시성을 보장하지 않는걸로 보임... 내가 잘 못쓰는건지
                // redisConn.set(bagKeyByteArray, newSizeWithUUID.toByteArray())

                // (2) 아래의 방식도 완전한 동시성을 보장하지 않는다. 별도 요청이 동시에 multi block 으로 들어오면? null 반환없이 올바르게 수행됨
                // redis 를 이용하더라도 씹히는 경우가 계속 발생한다.
                val newSize = redisConn.incr(bagKeyByteArray) ?: 0

                // exec 를 수행이전이기 때문에 이전 데이터
                if (newSize + 1 != bag.size + 1) {
                    // mysql 에 저장된 데이터와 같이 보완해서 처리가 될 수 있도록 한다.
                    throw RuntimeException("레디스 incr 로 증가된 값과 아이템 증가될 예측 사이즈 값이 서로 다릅니다.")
                }

                // When using WATCH, EXEC can return a Null reply if the execution was aborted.
                // https://redis.io/commands/exec/ 참고, null 이 반환되면 애플리케이션 단에서는 에러를 발생시킬 수 있도록 한다.
                redisConn.exec() ?: throw RuntimeException("레디스에 클라이언트 동시 요청이 들어왔습니다.")
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