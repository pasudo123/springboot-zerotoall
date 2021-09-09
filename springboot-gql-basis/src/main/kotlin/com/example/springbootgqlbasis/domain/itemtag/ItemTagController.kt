package com.example.springbootgqlbasis.domain.itemtag

import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("item-tags")
class ItemTagController(
    private val itemTagRepository: ItemTagRepository
) {

    @GetMapping("{id}")
    fun findOneById(@PathVariable id: Long): ItemTag? {
        return itemTagRepository.findByIdOrNull(id)
    }

    @GetMapping
    fun findAll(): List<ItemTag> {
        return itemTagRepository.findAll()
    }
}