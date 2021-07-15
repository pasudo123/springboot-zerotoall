package com.example.springbootconcurrencybasis.domain.concert.api

import com.example.springbootconcurrencybasis.domain.concert.api.dto.ConcertCreateDto
import com.example.springbootconcurrencybasis.domain.concert.model.Concert
import com.example.springbootconcurrencybasis.domain.concert.repository.ConcertRepository
import com.example.springbootconcurrencybasis.domain.concert.service.ConcertFindService
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Transactional
@RestController
@RequestMapping("concerts")
class ConcertController(
    private val concertRepository: ConcertRepository,
    private val concertFindService: ConcertFindService,
) {

    @PostMapping
    fun create(
        @RequestBody concertCreateDto: ConcertCreateDto
    ): ResponseEntity<Concert> {
        val concert = concertRepository.save(Concert.from(concertCreateDto))
        return ResponseEntity.ok(concert)
    }

    @GetMapping("{id}")
    fun findOneById(
        @PathVariable id: Long
    ): ResponseEntity<Concert> {
        val concert = concertFindService.findOneByIdOrThrow(id)
        return ResponseEntity.ok(concert)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<Concert>> {
        val concerts = concertRepository.findAll()
        return ResponseEntity.ok(concerts)
    }
}