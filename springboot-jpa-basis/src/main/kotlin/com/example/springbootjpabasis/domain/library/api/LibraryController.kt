package com.example.springbootjpabasis.domain.library.api

import com.example.springbootjpabasis.domain.library.api.dto.LibraryCreateDto
import com.example.springbootjpabasis.domain.library.model.Library
import com.example.springbootjpabasis.domain.library.repository.LibraryRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "서점 도메인 API")
@RestController
@RequestMapping("/libraries")
@Transactional
class LibraryController(
    private val libraryRepository: LibraryRepository
) {

    @Operation(summary = "서점을 추가한다.")
    @PostMapping
    fun create(@RequestBody libraryCreateDto: LibraryCreateDto): ResponseEntity<Library> {
        val library = libraryRepository.save(Library.from(libraryCreateDto))
        return ResponseEntity.ok(library)
    }

    @Operation(summary = "서점을 전체 조회한다.")
    @GetMapping
    fun fetchAll(): ResponseEntity<List<Library>> {
        val libraries = libraryRepository.findAll()
        return ResponseEntity.ok(libraries)
    }

    @Operation(summary = "서점을 단일 조회한다.")
    @GetMapping("{id}")
    fun fetchOnById(@PathVariable("id") id: Long): ResponseEntity<Library> {
        val libraryOpt = libraryRepository.findById(id)
        if (libraryOpt.isEmpty) {
            throw EntityNotFoundException("Does not exist library[$id]")
        }

        return ResponseEntity.ok(libraryOpt.get())
    }
}
