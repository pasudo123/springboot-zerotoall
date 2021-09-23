package com.example.springbootgqlbasis.domain.itemtag

import mu.KLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityNotFoundException

@Transactional
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
    ): ResponseEntity<ItemTagResources.Response> {
        state?.let { logger.info { "getHeader [state]=${state}" } }
        val itemTag = itemTagRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("아이템 태그를 찾지 못했습니다.")
        return ResponseEntity.ok(ItemTagResources.Response.from(itemTag))
    }

    @GetMapping
    fun findAll(
        @RequestHeader(value = "state", required = false) state: String?
    ): ResponseEntity<List<ItemTagResources.Response>> {
        state?.let { logger.info { "getHeader [state]=${state}" } }
        val itemTags = itemTagRepository.findAll()
        val responses = itemTags.map { itemTag ->
            ItemTagResources.Response.from(itemTag)
        }
        return ResponseEntity.ok(responses)
    }
}