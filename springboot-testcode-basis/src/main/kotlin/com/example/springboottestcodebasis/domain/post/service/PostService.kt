package com.example.springboottestcodebasis.domain.post.service

import com.example.springboottestcodebasis.domain.post.PostResources
import com.example.springboottestcodebasis.domain.post.model.Post
import com.example.springboottestcodebasis.domain.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository
) {

    fun create(request: PostResources.CreateRequest): Long {
        val post = Post(
            paramTitle = request.title,
            paramContent = request.content
        )

        return postRepository.save(post).id!!
    }

    fun findOneByIdOrThrow(id: Long): Post {
        return postRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("게시물을 찾지 못했습니다.")
    }
}