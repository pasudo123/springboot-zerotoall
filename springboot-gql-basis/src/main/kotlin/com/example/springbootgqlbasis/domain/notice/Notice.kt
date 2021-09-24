package com.example.springbootgqlbasis.domain.notice

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "notice")
class Notice (
    @Column(name = "title")
    val title: String,
    @Column(name = "contents")
    val contents: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(name = "votes")
    var votes: Long = 0
        protected set

    fun plusVotes() {
        votes++
    }
}