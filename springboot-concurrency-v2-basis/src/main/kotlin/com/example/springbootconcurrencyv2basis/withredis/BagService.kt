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
                // redis 는 atomic 하더라도, mysql 은 atomic 하지 않을 수 있다.
                // 이를 해결하는 방법은 mysql 에도 optimistic lock 을 같이 적용시켜두는 것이 해결책이 될 수 있다.
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