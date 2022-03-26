package com.example.springbootjpabasis.domain.book.model

import com.example.springbootjpabasis.domain.BaseEntity
import org.hibernate.envers.Audited
import org.hibernate.envers.RelationTargetAuditMode
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MapsId
import javax.persistence.OneToOne
import javax.persistence.Table

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "book_detail")
class BookDetail(
    @Column(columnDefinition = "TEXT")
    var content: String
) : BaseEntity(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToOne(targetEntity = Book::class)
    @MapsId
    var book: Book? = null
        protected set

    fun setBy(book: Book) {
        this.book = book
        book.setBy(this)
    }
}