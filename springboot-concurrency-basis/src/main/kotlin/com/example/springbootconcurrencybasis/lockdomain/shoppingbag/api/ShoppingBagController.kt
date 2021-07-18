package com.example.springbootconcurrencybasis.lockdomain.shoppingbag.api

import com.example.springbootconcurrencybasis.lockdomain.shoppingbag.api.dto.ShoppingBagDto
import com.example.springbootconcurrencybasis.lockdomain.shoppingbag.model.ShoppingBag
import com.example.springbootconcurrencybasis.lockdomain.shoppingbag.repository.ShoppingBagRepository
import com.example.springbootconcurrencybasis.lockdomain.snack.api.dto.SnackDto
import com.example.springbootconcurrencybasis.lockdomain.snack.model.Snack
import com.example.springbootconcurrencybasis.lockdomain.snack.repository.SnackRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("shopping-bags")
@Transactional
class ShoppingBagController(
    private val shoppingBagRepository: ShoppingBagRepository,
    private val snackRepository: SnackRepository
) {

    @PostMapping
    fun create(
        @RequestBody createRequest: ShoppingBagDto.CreateRequest
    ): ResponseEntity<ShoppingBag> {
        val shoppingBag = shoppingBagRepository.save(ShoppingBag.from(createRequest))
        return ResponseEntity.ok(shoppingBag)
    }

    @PostMapping("{id}/snack")
    fun addSnack(
        @PathVariable("id") id: Long,
        @RequestBody createRequest: SnackDto.CreateRequest
    ): ResponseEntity<ShoppingBag> {
        val shoppingBag = shoppingBagRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("Does not found shopping bag[$id]")
        val snack = snackRepository.save(Snack.from(createRequest))
        shoppingBag.addSnack(snack)
        return ResponseEntity.ok(shoppingBag)
    }

    @Transactional(readOnly = true)
    @GetMapping("{id}")
    fun findById(
        @PathVariable("id") id: Long
    ): ResponseEntity<ShoppingBag> {
        val shoppingBag = shoppingBagRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("Does not found shopping bag[$id]")
        return ResponseEntity.ok(shoppingBag)
    }
}