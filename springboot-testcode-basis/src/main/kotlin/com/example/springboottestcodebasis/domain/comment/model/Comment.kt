package com.example.springboottestcodebasis.domain.comment.model

import com.example.springboottestcodebasis.domain.post.model.Post
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "comment")
class Comment(
    paramContent: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(name = "content", nullable = false)
    var content: String = paramContent
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post? = null
        protected set

    fun setBy(post: Post) {
        this.post = post
    }
}