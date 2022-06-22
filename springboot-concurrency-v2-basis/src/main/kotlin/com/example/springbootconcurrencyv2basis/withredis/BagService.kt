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
        log.info("called")

        val bag = bagRepository.findByIdOrNull(id)
            ?: throw RuntimeException("가방 미확인 : $id")

        if (withWatch) {
            try {
                bagRedisRepository.incrItemWithWatchOrThrow(bag)
                bag.addItemIfPossibleOrThrow()
            } catch (exception: Exception) {
                log.info("error : ${exception.message}")
            }

            return bag
        }

        try {
            bagRedisRepository.incrItemOrThrow(bag)
            bag.addItemIfPossibleOrThrow()
        } catch (exception: Exception) {
            log.info("error : ${exception.message}")
        }

        return bag
    }
}