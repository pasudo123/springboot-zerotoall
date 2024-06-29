package com.example.springbootmongobasis.poc01.api

import com.example.springbootmongobasis.poc01.domain.Cafe
import com.example.springbootmongobasis.poc01.domain.CafeRepository
import com.example.springbootmongobasis.util.toObjectIdOrNull
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("cafes")
class CafeController(
    private val cafeRepository: CafeRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("count")
    fun countAllMenu(): ResponseEntity<CafeMenuResponse> {
        val cafes = cafeRepository.findAll()
        val response = CafeMenuResponse(
            cafeCount = cafes.count(),
            coffeeCount = cafes.flatMap { it.coffees }.count(),
            beverageCount = cafes.flatMap { it.beverages }.count(),
            teeCount = cafes.flatMap { it.tees }.count()
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping("{id}")
    fun findCafe(@PathVariable id: String): ResponseEntity<Cafe> {
        val cafe = id.toObjectIdOrNull()?.let { cafeRepository.findByIdOrNull(it) }
            ?: throw RuntimeException("id=$id 에 대한 cafe 미존재.")
        return ResponseEntity.ok(cafe)
    }

    @PutMapping("{id}/sub/{menuId}")
    fun updateCafeMenu(
        @PathVariable id: String,
        @PathVariable menuId: String
    ): ResponseEntity<Unit> {
        val cafe = id.toObjectIdOrNull()?.let { cafeRepository.findByIdOrNull(ObjectId(id)) }
            ?: throw RuntimeException("id=$id 에 대한 cafe 미존재.")

        return ResponseEntity.ok(Unit)
    }
}