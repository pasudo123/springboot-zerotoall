package com.example.springbootgqlbasis.domain.item

import mu.KLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("items")
class ItemController(
    private val itemRepository: ItemRepository
) {

    companion object : KLogging()

    @GetMapping("{id}")
    fun findOneById(
        @RequestHeader(value = "state", required = false) state: String?,
        @PathVariable id: Long
    ): Item? {
        state?.let { logger.info { "getHeader [state]=${state}" } }
        return itemRepository.findByIdOrNull(id)
    }

    @GetMapping
    fun findAll(
        @RequestHeader(value = "state", required = false) state: String?
    ): List<Item> {
        state?.let { logger.info { "getHeader [state]=${state}" } }
        return itemRepository.findAll()
    }
}