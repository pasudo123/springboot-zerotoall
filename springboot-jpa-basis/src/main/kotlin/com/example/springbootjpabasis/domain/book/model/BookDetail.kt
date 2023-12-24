package com.example.springbootjpabasis.domain.book.model

import com.example.springbootjpabasis.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.envers.Audited
import org.hibernate.envers.RelationTargetAuditMode

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "book_detail")
class BookDetail(
    @Column(columnDefinition = "TEXT")
    var content: String
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(targetEntity = Book::class)
    var book: Book? = null
        protected set

    fun setBy(book: Book) {
        this.book = book
        book.setBy(this)
    }
}
