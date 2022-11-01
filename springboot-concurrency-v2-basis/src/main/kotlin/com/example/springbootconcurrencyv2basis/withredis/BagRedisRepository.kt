package com.example.springbootconcurrencyv2basis.withredis

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SessionCallback
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

@Repository
class BagRedisRepository(
    @Qualifier("bagRedisLettuceTemplate")
    private val bagRedisLettuceTemplate: RedisTemplate<String, String>,
) {

    private val log = LoggerFactory.getLogger(javaClass)

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

    /**
     * 레디스 싱글 스레드 동작 및 incr 을 통해서 원자성을 보장토록 한다.
     * 1초 내에 특정 키에 대한 동시요청을 허용하지 않는다.
     */
    fun incrItemOrThrow(bag: Bag): Long {
        val bagKey = "bag:${bag.id!!}"

        bagRedisLettuceTemplate.expire(bagKey, 500L, TimeUnit.MILLISECONDS)
        val incrSize = bagRedisLettuceTemplate.opsForValue().increment(bagKey)!!

        log.info("incrSize : $incrSize")
        if (incrSize > 1) {
            throw RuntimeException("아이템을 다시 추가해주세요.")
        }

        return incrSize
    }

    fun unlinkItem(bag: Bag) {
        val bagKey = "bag:${bag.id!!}"
        bagRedisLettuceTemplate.unlink(bagKey)
    }

    /**
     * 이렇게 한다한들? mysql 은 race condition 에 취약함.
     */
    fun incrItemWithWatchAndZPopMaxOrThrow(bag: Bag): Long? {
        val zSetBag = "z:bag:${bag.id!!}"
        val bagKey = "bag:${bag.id!!}"

        return bagRedisLettuceTemplate.execute(object : SessionCallback<Long> {
            @JvmName("executeByCustomOperation")
            fun <K : String?, V : String?> execute(operations: RedisOperations<String, String>): Long? {

                return try {
                    operations.watch(bagKey)

                    // value 를 가변적으로 바꾸려고 한다면
                    val randomUUID = UUID.randomUUID().toString().substring(0, 5)
                    val size = operations.opsForValue().get(bagKey)
                    val newSize = if (size == null) 1 else size.split(":").first().toInt() + 1
                    val newSizeWithUUID = "$newSize:$randomUUID"

                    operations.multi()
                    operations.opsForValue().set(bagKey, newSizeWithUUID)
                    operations.opsForZSet().add(bagKey, randomUUID, newSize.toDouble())
                    operations.exec()

                    operations.opsForZSet().zCard(bagKey)
                } catch (exception: Exception) {
                    throw RuntimeException("레디스에 클라이언트 동시 요청이 들어왔습니다. : ${exception.message}")
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun <K : Any?, V : Any?> execute(operations: RedisOperations<K, V>): Long? {
                return this.execute<String, String>(operations as RedisOperations<String, String>)
            }
        })
    }

    fun incrItemWithWatchOrThrow(bag: Bag) {
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
                // ** 요청으로 인한 redis 는 race condition 에 락을 적용시킬 수 있더라도 mysql 데이터의 정합성까지 같이 맞출 순 없다. 그래서 mysql lock 도 같이처리?
                try {
                    val results = redisConn.exec()
                    log.info("Result :: $results")
                } catch (exception: Exception) {
                    // exception.message : "redisConn.exec() must not be null"
                    throw RuntimeException("레디스에 클라이언트 동시 요청이 들어왔습니다. : ${exception.message}")
                }
            }
        }
    }
}
