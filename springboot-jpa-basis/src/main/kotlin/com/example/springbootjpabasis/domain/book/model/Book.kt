package com.example.springbootjpabasis.domain.book.model

import com.example.springbootjpabasis.domain.BaseEntity
import com.example.springbootjpabasis.domain.book.api.dto.BookCreateDto
import com.example.springbootjpabasis.domain.library.model.Library
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.envers.Audited
import java.util.UUID

@Audited
@Entity
@Table(
    name = "book",
    indexes = [
        Index(name = "isbn_idx", columnList = "isbn")
    ]
)
class Book(

    @Column(name = "name", columnDefinition = "VARCHAR(80)", length = 80, nullable = false)
    val name: String,

    @Column(name = "author", columnDefinition = "VARCHAR(60)", length = 60, nullable = false)
    val author: String,

    @Column(name = "publisher", columnDefinition = "VARCHAR(50)", length = 50, nullable = false)
    val publisher: String,

    @Column(name = "isbn", columnDefinition = "VARCHAR(80)", length = 80, nullable = false)
    val isbn: String
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(
        mappedBy = "book",
        fetch = FetchType.LAZY,
        targetEntity = BookDetail::class
    )
    var detail: MutableList<BookDetail> = mutableListOf()

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id")
    var library: Library? = null
        protected set

    fun setByLibrary(library: Library) {
        if (this.library == null) {
            this.library = library
        }

        this.library!!.addBook(this)
    }

    fun setBy(detail: BookDetail) {
        this.detail.add(detail)
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
