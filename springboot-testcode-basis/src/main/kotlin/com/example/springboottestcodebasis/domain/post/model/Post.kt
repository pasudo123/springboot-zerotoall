package com.example.springboottestcodebasis.domain.post.model

import com.example.springboottestcodebasis.domain.comment.model.Comment
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "post")
class Post (
    paramTitle: String,
    paramContent: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(name = "title", nullable = false)
    var title: String = paramTitle
        protected set

    @Column(name = "content", nullable = false)
    var content: String = paramContent
        protected set

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private val _comments: MutableList<Comment> = mutableListOf()
    val comments: List<Comment>
        get() = _comments.toList()

    fun addComment(comment: Comment) {
        if(_comments.map { it.id }.contains(comment.id)) {
            return
        }

        _comments.add(comment)
        comment.setBy(this)
    }
}
