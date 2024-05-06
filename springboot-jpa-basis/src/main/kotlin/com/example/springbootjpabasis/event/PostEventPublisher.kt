package com.example.springbootjpabasis.event

import com.example.springbootjpabasis.domain.post.api.dto.PostCreateDto
import com.example.springbootjpabasis.domain.post.model.Post
import com.example.springbootjpabasis.domain.post.repository.PostRepository
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime

@Component
class PostEventPublisher(
    private val postRepository: PostRepository,
    private val publisher: ApplicationEventPublisher
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun publish(postCreateDto: PostCreateDto) {
        log.info("publish-enter... now=${LocalTime.now()}")
        val post = postRepository.saveAndFlush(Post.from(postCreateDto))
        post.update("Hello")
        publisher.publishEvent(PostEvent(post.id!!))
        Thread.sleep(2000)
        log.info("publish-end... now=${LocalTime.now()}")
    }
}
