package com.example.springboottestcodebasis.domain.comment.api

import com.example.springboottestcodebasis.domain.comment.CommentResources
import com.example.springboottestcodebasis.domain.comment.model.Comment
import com.example.springboottestcodebasis.domain.comment.repository.CommentRepository
import com.example.springboottestcodebasis.domain.post.repository.PostRepository
import com.example.springboottestcodebasis.domain.post.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
class CommentController(
    private val postRepository: PostRepository,
    private val postService: PostService,
    private val commentRepository: CommentRepository
) {

    @PostMapping("posts/{id}/comments")
    fun create(
        @PathVariable("id") id: Long,
        @RequestBody request: CommentResources.CreateRequest
    ): ResponseEntity<Long> {
        val comment = Comment(
            paramContent = request.content
        )
        commentRepository.save(comment)
        val post = postService.findOneByIdOrThrow(id)
        post.addComment(comment)
        postRepository.save(post)

        return ResponseEntity.ok(comment.id!!)
    }
}