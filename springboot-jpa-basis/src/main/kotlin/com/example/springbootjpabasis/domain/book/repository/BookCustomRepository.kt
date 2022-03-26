package com.example.springbootjpabasis.domain.book.repository

import com.example.springbootjpabasis.domain.book.model.Book
import com.example.springbootjpabasis.domain.book.model.QBook.book
import com.example.springbootjpabasis.domain.book.model.QBookDetail.bookDetail
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class BookCustomRepository(
    private val queryFactory: JPAQueryFactory
) : QuerydslRepositorySupport(Book::class.java) {

    fun findAllWithDetail(): List<Book> {
        return queryFactory.selectFrom(book)
            .leftJoin(book.detail, bookDetail).fetchJoin()
            .fetch()
    }
}