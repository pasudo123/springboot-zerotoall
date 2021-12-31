package com.example.springboottestcodebasis.domain.post.api

import com.example.springboottestcodebasis.domain.post.PostResources
import com.example.springboottestcodebasis.domain.post.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("posts")
class PostController(
    private val postService: PostService,
) {

    @PostMapping
    fun create(@RequestBody request: PostResources.CreateRequest): ResponseEntity<Long> {
        return ResponseEntity.ok(postService.create(request))
    }

    @GetMapping("{id}")
    fun findOneById(@PathVariable("id") id: Long): ResponseEntity<PostResources.ResponseWithComment> {
        val post = postService.findOneByIdOrThrow(id)
        return ResponseEntity.ok(PostResources.ResponseWithComment.from(post))
    }


}