package com.example.springbootjpabasis.domain.book.repository

import com.example.springbootjpabasis.RepositorySupport
import com.example.springbootjpabasis.domain.book.api.dto.BookCreateDto
import com.example.springbootjpabasis.domain.book.model.Book
import com.example.springbootjpabasis.domain.book.model.BookDetail
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import kotlin.random.Random

@RepositorySupport
internal class BookRepositoryTest(
    private val entityManager: TestEntityManager,
    private val bookRepository: BookRepository
    // private val bookCustomRepository: BookCustomRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Test
    fun findAllTest() {
        repeat(5) {
            val book = Book.from(
                BookCreateDto(
                    "책이름-${Random.nextLong(10, 99)}",
                    "작가-${Random.nextLong(10, 99)}",
                    "출발판-${Random.nextLong(10, 99)}",
                    Random.nextLong(10000, 99999)
                )
            )
            entityManager.persist(book)
            val detail = BookDetail("책상세-${Random.nextLong(10000, 99999)}")
            detail.setBy(book)
            entityManager.persist(detail)
        }

        log.info("flush & clear start ========================================================================")
        entityManager.flush()
        entityManager.clear()
        log.info("flush & clear end ========================================================================")

        // when
        log.info("findAll() ========================================================================")
        val books = bookRepository.findAll()

        // then
        books.size shouldBe 5
    }

    @Test
    @Disabled("테스트코드가 원래 동작했는데, 다시 안된다.. ㅠ")
    fun findAllWithDetailTest() {
        repeat(5) {
            val book = Book.from(
                BookCreateDto(
                    "책이름-${Random.nextLong(10, 99)}",
                    "작가-${Random.nextLong(10, 99)}",
                    "출발판-${Random.nextLong(10, 99)}",
                    Random.nextLong(10000, 99999)
                )
            )
            entityManager.persist(book)
            val detail = BookDetail("책상세-${Random.nextLong(10000, 99999)}")
            detail.setBy(book)
            entityManager.persist(detail)
        }

        log.info("flush & clear start ========================================================================")
        entityManager.flush()
        entityManager.clear()
        log.info("flush & clear end ========================================================================")

        // when
        log.info("findAll() ========================================================================")
//        val books = bookCustomRepository.findAllWithDetail()
//
//        // then
//        books.size shouldBe 5
    }
}
