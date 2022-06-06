package com.example.springbootjpabasis.domain.post.repository

import com.example.springbootjpabasis.RepositorySupport
import com.example.springbootjpabasis.domain.post.model.Post
import com.example.springbootjpabasis.domain.postdetail.model.PostDetail
import com.example.springbootjpabasis.domain.postdetail.repository.PostDetailRepository
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.dao.DataIntegrityViolationException
import javax.persistence.EntityExistsException

@RepositorySupport
internal class PostRepositoryTest(
    private val entityManager: TestEntityManager,
    private val postRepository: PostRepository,
    private val postDetailRepository: PostDetailRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @BeforeEach
    fun init () {
        postDetailRepository.deleteAll()
        postRepository.deleteAll()
    }

    @Test
    @DisplayName("post 를 저장/조회한다.")
    fun saveAndFindAllTest() {

        // given
        val content = "안녕하세요."
        postRepository.saveAndFlush(Post(content))
        entityManager.clear()

        // when
        val posts = postRepository.findAll()

        // then
        posts.size shouldBe 1
    }

    @Test
    @DisplayName("post 를 저장, postDetail 도 같이 저장한다.")
    fun saveAndPostDetailSaveTest() {

        // given
        val content = "안녕하세요."
        val post = Post(content)
        postRepository.saveAndFlush(post)
        entityManager.clear()

        // when
        val postDetail = PostDetail(listOf("tag1", "tag2"))
        postDetail.setBy(post)
        postRepository.saveAndFlush(post)

        // then
        post.id shouldNotBe null
        postDetail.id shouldBe null
        postDetail.post?.id shouldBe post.id
    }

    @Test
    @DisplayName("post 를 저장, postDetail 의 식별자가 존재함에도 불구하고 저장하려고 해서 에러 발생")
    fun saveAndPostDetailDupSaveErrorTest() {

        val post = Post("안녕하세요.")
        postRepository.saveAndFlush(post)
        entityManager.clear()

        val postDetail = PostDetail(listOf("tag1", "tag2"))
        post.setBy(postDetail)

        postRepository.saveAndFlush(post)
        entityManager.clear()

        val newPostDetail = PostDetail(listOf("tag3", "tag4"))
        post.setBy(newPostDetail)

        val exception = assertThrows<DataIntegrityViolationException> {
            postRepository.saveAndFlush(post)
        }
        entityManager.clear()

        exception.asClue {
            (it.cause is EntityExistsException) shouldBe true
            it.message shouldContain "A different object with the same identifier value was already associated with the session"
        }

        postRepository.findAll().size shouldBe 1
        postDetailRepository.findAll().size shouldBe 1
    }

    @Test
    @DisplayName("post 를 저장, postDetail 의 식별자가 존재하는지 확인 후 다시 저장. 에러는 발생 안함")
    fun saveAndPostDetailDupSaveTest() {

        val post = Post("안녕하세요.")
        postRepository.saveAndFlush(post)
        entityManager.clear()

        val postDetail = PostDetail(listOf("tag1", "tag2"))
        post.setBy(postDetail)
        postRepository.saveAndFlush(post)
        entityManager.clear()

        val newPostDetail = postDetailRepository.findByPostId(post.id!!) ?: PostDetail(listOf("tag3", "tag4"))
        post.setBy(newPostDetail)

        postRepository.saveAndFlush(post)
        entityManager.clear()

        postRepository.findAll().size shouldBe 1
        postDetailRepository.findAll().size shouldBe 1
    }
}
