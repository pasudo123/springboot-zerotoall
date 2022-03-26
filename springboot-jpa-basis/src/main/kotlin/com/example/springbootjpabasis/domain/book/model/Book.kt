package com.example.springbootjpabasis.domain.book.model

import com.example.springbootjpabasis.domain.BaseEntity
import  com.example.springbootjpabasis.domain.book.api.dto.BookCreateDto
import com.example.springbootjpabasis.domain.library.model.Library
import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.envers.Audited
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Audited
@Entity
@Table(name = "book", indexes = [
    Index(name = "isbn_idx", columnList = "isbn")
])
class Book(

    @Column(name = "name", columnDefinition = "VARCHAR(80)", length = 80, nullable = false)
    val name: String,

    @Column(name = "author", columnDefinition = "VARCHAR(60)", length = 60, nullable = false)
    val author: String,

    @Column(name = "publisher", columnDefinition = "VARCHAR(50)", length = 50, nullable = false)
    val publisher: String,

    @Column(name = "isbn", columnDefinition = "VARCHAR(80)", length = 80, nullable = false)
    val isbn: String
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id")
    var library: Library? = null
        protected set

    fun setByLibrary(library: Library) {
        if(this.library == null) {
            this.library = library
        }

        this.library!!.addBook(this)
    }

    companion object {
        fun from(bookCreateDto: BookCreateDto): Book {
            bookCreateDto.run {
                return Book(
                    name,
                    author,
                    publisher,
                    UUID.randomUUID().toString()
                )
            }
        }
    }
}