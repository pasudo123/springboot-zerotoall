package com.example.springbootgqlbasis.domain.itemtag

import mu.KLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("item-tags")
class ItemTagController(
    private val itemTagRepository: ItemTagRepository
) {

    companion object : KLogging()

    @GetMapping("{id}")
    fun findOneById(
        @RequestHeader(value = "state", required = false) state: String?,
        @PathVariable id: Long
    ): ItemTag? {
        state?.let { logger.info { "getHeader [state]=${state}" } }
        return itemTagRepository.findByIdOrNull(id)
    }

    @GetMapping
    fun findAll(
        @RequestHeader(value = "state", required = false) state: String?
    ): List<ItemTag> {
        state?.let { logger.info { "getHeader [state]=${state}" } }
        return itemTagRepository.findAll()
    }
}