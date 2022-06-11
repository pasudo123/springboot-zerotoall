package com.example.springbootconcurrencyv2basis.withredis

import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.StringRedisConnection
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class BagService(
    private val bagRepository: BagRepository,
    private val bagRedisTemplate: StringRedisTemplate
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun addItemByBagId(id: Long): Bag {
        println("> 들어왔음")
        val key = "bag:$id:size"

        val result = bagRedisTemplate.execute {
            println("execute --> : $key")
            it.watch(key.toByteArray())

            it.multi()
            val size = it.get(key.toByteArray())?.toString() ?: "0"
            it.setNX(key.toByteArray(), "${size.toInt() + 1}".toByteArray())
            return@execute it.exec()
        }

        println(result)

//        bagRedisTemplate.execute(object: StringSessionCallback {
//            override fun <K : String?, V : String?> execute(operations: StringRedisOperation): List<Any>? {
//
//                operations.watch(key)
//                operations.multi()
//                val size = operations.opsForValue().get(key) ?: "0"
//                operations.opsForValue().set(key, "${size.toInt() + 1}")
//
//                return operations.exec()
//            }
//        })

        val bag = bagRepository.findByIdOrNull(id)
            ?: throw RuntimeException("가방 미확인 : $id")

        try {
            bag.addItemIfPossibleOrThrow()
        } catch (exception: Exception) {
            val size = bagRedisTemplate.opsForValue().get(key) ?: "1"
            bagRedisTemplate.opsForValue().setIfAbsent(key, "${size.toInt() - 1}")
            log.info(":: 가방에 아이템이 가득 찼습니다. ::")
            throw RuntimeException("가방에 아이템이 가득찼습니다.")
        }

        return bag
    }
}