package com.example.springboottestcodebasis.domain.comment

import com.example.springboottestcodebasis.domain.comment.model.Comment

class CommentResources {

    data class CreateRequest(
        val content: String
    )

    data class Response(
        val id: Long,
        val content: String
    ) {
        companion object {
            fun from(comment: Comment): Response {
                return comment.run {
                    Response(
                        id = this.id!!,
                        content = this.content
                    )
                }
            }
        }
    }
}