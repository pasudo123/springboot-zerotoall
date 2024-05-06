package com.example.springbootjpabasis.domain.post

import com.example.springbootjpabasis.domain.post.api.dto.PostCreateDto
import com.example.springbootjpabasis.domain.post.model.Post
import com.example.springbootjpabasis.domain.post.repository.PostRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postInsertService: PostInsertService,
    private val postRepository: PostRepository
) {

    // self 인젝션 처리시 순환참조 발생. 결론적으로 사용하지 못함.
    // @Autowired
    // private lateinit var self: PostService

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun create(dto: PostCreateDto) {
        // val initPost = postInsertService.createIndependently(dto)

        val initPost = innerCreateIndependently(dto)

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

    /**
     * 내부 메소드에 전파레벨이 requires_new 라고 하더라도 상위 메소드 익셉션 시 같이 롤백됨.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun innerCreateIndependently(dto: PostCreateDto): Post {
        log.info("#### 같은 클래스, @Transactional 호출")
        return postRepository.saveAndFlush(Post.from(dto))
    }

    @Transactional
    fun createWithDetail(dto: PostCreateDto) {
        val post = postRepository.save(Post.from(dto))
    }
}
