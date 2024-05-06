package com.example.springbootjpabasis.domain.post

import com.example.springbootjpabasis.domain.post.api.dto.PostCreateDto
import com.example.springbootjpabasis.domain.post.model.Post
import com.example.springbootjpabasis.domain.post.repository.PostRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class PostInsertService(
    private val postRepository: PostRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun createIndependently(dto: PostCreateDto): Post {
        log.info("#### createIndependently")
        return postRepository.saveAndFlush(Post.from(dto))
    }
}
