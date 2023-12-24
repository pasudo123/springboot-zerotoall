package com.example.springbootjpabasis.domain.book.api

import com.example.springbootjpabasis.domain.book.api.dto.BookCreateDto
import com.example.springbootjpabasis.domain.book.model.Book
import com.example.springbootjpabasis.domain.book.repository.BookRepository
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

@Tag(name = "책 도메인 API")
@RestController
@RequestMapping("books")
@Transactional
class BookController(
    private val bookRepository: BookRepository,
    private val libraryRepository: LibraryRepository
) {

    @Operation(summary = "책을 추가한다.")
    @PostMapping
    fun create(@RequestBody bookCreateDto: BookCreateDto): ResponseEntity<Book> {
        val libraryOpt = libraryRepository.findById(bookCreateDto.libraryId)
        if (libraryOpt.isEmpty) {
            throw EntityNotFoundException("There is no library[${bookCreateDto.libraryId}] for adding book")
        }

        val book = bookRepository.save(Book.from(bookCreateDto))
        book.setByLibrary(libraryOpt.get())
        return ResponseEntity.ok(book)
    }

    @Operation(summary = "책을 전체 조회한다.")
    @GetMapping
    fun fetchAll(): ResponseEntity<List<Book>> {
        val books = bookRepository.findAll()
        return ResponseEntity.ok(books)
    }

    @Operation(summary = "책을 단일 조회한다.")
    @GetMapping("{id}")
    fun fetchOneById(@PathVariable("id") id: Long): ResponseEntity<Book> {
        val bookOpt = bookRepository.findById(id)
        if (bookOpt.isEmpty) {
            throw EntityNotFoundException("Does not exist book[$id]")
        }

        return ResponseEntity.ok(bookOpt.get())
    }
}
