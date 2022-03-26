package com.example.springbootjpabasis.domain.book.repository

import com.example.springbootjpabasis.RepositorySupport
import com.example.springbootjpabasis.domain.book.api.dto.BookCreateDto
import com.example.springbootjpabasis.domain.book.model.Book
import com.example.springbootjpabasis.domain.book.model.BookDetail
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import kotlin.random.Random

@RepositorySupport
internal class BookRepositoryTest(
    private val entityManager: TestEntityManager,
    private val bookRepository: BookRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Test
    fun findAllTest() {
        repeat(5) {
            val book = Book.from(BookCreateDto(
                "책이름-${Random.nextLong(10, 99)}",
                "작가-${Random.nextLong(10, 99)}",
                "출발판-${Random.nextLong(10, 99)}",
                Random.nextLong(10000, 99999)
            ))
            entityManager.persist(book)
            val detail = BookDetail("책상세-${Random.nextLong(10000, 99999)}")
            detail.book = book
            entityManager.persist(detail)
        }

        entityManager.flush()
        entityManager.clear()
        log.info("flush & clear ========================================================================")

        // when
        val books = bookRepository.findAll()
        log.info("findAll() ========================================================================")

        // then
        books.size shouldBe 5
    }
}