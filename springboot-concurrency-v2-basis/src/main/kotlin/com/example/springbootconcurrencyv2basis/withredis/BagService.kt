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

    fun addItemByBagId(id: Long): Bag {
        println("called")

        val bag = bagRepository.findByIdOrNull(id)
            ?: throw RuntimeException("가방 미확인 : $id")

        bagRedisRepository.incrItemOrThrow(bag)


        try {
            bag.addItemIfPossibleOrThrow()
        } catch (exception: Exception) {
            bagRedisRepository.decrItem(id)
            throw RuntimeException("가방에 아이템이 가득찼습니다.")
        }

        return bag
    }
}