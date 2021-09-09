package com.example.springbootgqlbasis.domain.item

import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("items")
class ItemController(
    private val itemRepository: ItemRepository
) {

    @GetMapping("{id}")
    fun findOneById(@PathVariable id: Long): Item? {
        return itemRepository.findByIdOrNull(id)
    }

    @GetMapping
    fun findAll(): List<Item> {
        return itemRepository.findAll()
    }
}