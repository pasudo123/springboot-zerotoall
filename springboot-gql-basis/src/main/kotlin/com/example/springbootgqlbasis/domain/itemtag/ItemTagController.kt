package com.example.springbootgqlbasis.domain.itemtag

import com.example.springbootgqlbasis.global.exception.ErrorCode
import com.example.springbootgqlbasis.global.exception.detail.DomainEntityNotFoundException
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
@RequestMapping("item-tags")
class ItemTagController(
    private val itemTagRepository: ItemTagRepository
) {

    companion object : KLogging()

    @GetMapping("{id}")
    fun findOneById(
        @RequestHeader(value = "flow", required = false) flow: String?,
        @PathVariable id: Long
    ): ResponseEntity<ItemTagResources.Response> {
        flow?.let { logger.info { "getHeader [flow]=${flow}" } }
        val itemTag = itemTagRepository.findByIdOrNull(id) ?: throw DomainEntityNotFoundException(ErrorCode.E100, "아이템 태그를 찾을 수 없습니다.")
        return ResponseEntity.ok(ItemTagResources.Response.from(itemTag))
    }

    @GetMapping
    fun findAll(
        @RequestHeader(value = "flow", required = false) flow: String?,
    ): ResponseEntity<List<ItemTagResources.Response>> {
        flow?.let { logger.info { "getHeader [flow]=${flow}" } }
        val itemTags = itemTagRepository.findAll()
        val responses = itemTags.map { itemTag ->
            ItemTagResources.Response.from(itemTag)
        }
        return ResponseEntity.ok(responses)
    }
}