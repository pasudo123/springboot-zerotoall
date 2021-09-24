package com.example.springbootgqlbasis.domain.item

import com.example.springbootgqlbasis.domain.itemtag.ItemTagResources
import com.example.springbootgqlbasis.global.exception.ErrorCode
import com.example.springbootgqlbasis.global.exception.detail.EntityNotFoundException
import mu.KLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Transactional
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
    ): ResponseEntity<ItemResources.Response> {
        state?.let { logger.info { "getHeader [state]=${state}" } }
        val item = itemRepository.findByIdOrNull(id) ?: throw EntityNotFoundException(ErrorCode.E100, "아이템을 찾을 수 없습니다.")
        return ResponseEntity.ok(ItemResources.Response.from(item))
    }

    @GetMapping
    fun findAll(
        @RequestHeader(value = "state", required = false) state: String?
    ): ResponseEntity<List<ItemResources.Response>> {
        state?.let { logger.info { "getHeader [state]=${state}" } }
        val items = itemRepository.findAll()
        val responses = items.map { item ->
            ItemResources.Response.from(item)
        }
        return ResponseEntity.ok(responses)
    }

    @GetMapping("{id}/tags")
    fun findTagsById(@PathVariable id: Long): ResponseEntity<List<ItemTagResources.Response>> {
        val item = itemRepository.findByIdOrNull(id) ?: throw EntityNotFoundException(ErrorCode.E100, "아이템태그를 찾을 수 없습니다.")
        val responses = item.itemTags.map { itemTag ->
            ItemTagResources.Response.from(itemTag)
        }
        return ResponseEntity.ok(responses)
    }
}