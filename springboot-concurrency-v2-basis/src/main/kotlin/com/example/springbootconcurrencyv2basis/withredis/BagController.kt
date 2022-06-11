package com.example.springbootconcurrencyv2basis.withredis

import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("bags")
class BagController(
    private val bagService: BagService,
    private val bagRepository: BagRepository
) {

    @PostMapping
    fun createBag(): Bag {
        return bagRepository.save(Bag())
    }

    @Transactional
    @PostMapping("{id}")
    fun addBagByInventoryId(@PathVariable id: Long): Bag {
        return this.bagService.addItemByBagId(id)
    }

    @GetMapping("{id}")
    fun getBagById(@PathVariable id: Long): Bag {
        return bagRepository.findByIdOrNull(id) ?: throw RuntimeException("가방 미확인 : $id")
    }
}

