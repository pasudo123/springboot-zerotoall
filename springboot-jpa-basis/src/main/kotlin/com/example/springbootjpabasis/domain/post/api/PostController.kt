package com.example.springbootjpabasis.domain.post.api

import com.example.springbootjpabasis.domain.post.api.dto.PostCreateDto
import com.example.springbootjpabasis.domain.post.model.Post
import com.example.springbootjpabasis.domain.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("posts")
@Transactional
class PostController(
    private val postRepository: PostRepository,
) {

    @PostMapping
    fun create(
        @RequestBody postCreateDto: PostCreateDto
    ): ResponseEntity<Post> {
        val post = Post.from(postCreateDto)
        postRepository.save(post)
        return ResponseEntity.ok().body(post)
    }

    @PatchMapping("{id}/contents")
    fun updateContents(
        @PathVariable("id") id: Long,
        @RequestBody postCreateDto: PostCreateDto
    ): ResponseEntity<Post> {
        val post = postRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("Post[$id] 를 찾을 수 없습니다.")
        post.update(postCreateDto.contents)
        return ResponseEntity.ok(post)
    }

    @GetMapping("{id}/histories")
    fun findHistoriesById(
        @PathVariable("id") id: Long
    ) {
        TODO("구현이 필요함")
    }
}