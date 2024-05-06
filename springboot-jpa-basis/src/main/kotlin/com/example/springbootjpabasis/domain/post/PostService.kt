package com.example.springbootjpabasis.domain.post

import com.example.springbootjpabasis.domain.post.api.dto.PostCreateDto
import com.example.springbootjpabasis.domain.post.model.Post
import com.example.springbootjpabasis.domain.post.repository.PostRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postInsertService: PostInsertService,
    private val postRepository: PostRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun create(dto: PostCreateDto) {
        val initPost = postInsertService.createIndependently(dto)

        try {
            log.info("#### try catch")
            initPost.update("update-${dto.contents}")
            postRepository.saveAndFlush(initPost)
            throw RuntimeException("강제에러 발생")
        } catch (exception: Exception) {
            log.error("message=${exception.message}")
            throw exception
        }
    }

    @Transactional
    fun createWithDetail(dto: PostCreateDto) {
        val post = postRepository.save(Post.from(dto))
    }
}
