package com.example.springbootconcurrencyv2basis.withredis

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class BagService(
    private val bagRepository: BagRepository,
    private val bagRedisRepository: BagRedisRepository,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun addItemByBagId(id: Long, withWatch: Boolean): Bag {
        println("called")

        val bag = bagRepository.findByIdOrNull(id)
            ?: throw RuntimeException("가방 미확인 : $id")

        if (withWatch) {
            bagRedisRepository.incrItemWithWatchOrThrow(bag)
            bag.addItemIfPossibleOrThrow()
            return bag
        }

        try {
            bagRedisRepository.incrItemOrThrow(bag)
            bag.addItemIfPossibleOrThrow()
            bagRedisRepository.unlinkItem(bag)
        } catch (exception: Exception) {
            println("error : ${exception.message}")
        }

        return bag
    }
}