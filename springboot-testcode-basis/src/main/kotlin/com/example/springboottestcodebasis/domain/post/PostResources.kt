package com.example.springboottestcodebasis.domain.post

import com.example.springboottestcodebasis.domain.comment.CommentResources
import com.example.springboottestcodebasis.domain.post.model.Post

class PostResources {

    data class CreateRequest(
        val title: String,
        val content: String
    )

    data class Response(
        val id: Long,
        val title: String,
        val content: String
    ) {
        companion object {
            fun from(post: Post) {
                return post.run {
                    Response(
                        id = this.id!!,
                        title = this.title,
                        content = this.content
                    )
                }
            }
        }
    }

    data class ResponseWithComment(
        val id: Long,
        val title: String,
        val content: String,
        val comments: List<CommentResources.Response>
    ) {

        companion object {
            fun from(post: Post): ResponseWithComment {
                return ResponseWithComment(
                    id = post.id!!,
                    title = post.title,
                    content = post.content,
                    comments = post.comments.map { CommentResources.Response.from(it) }
                )
            }
        }
    }
}