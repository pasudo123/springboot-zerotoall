package com.example.springboottestcodebasis.domain.post.api

import com.example.IntegrationSupport
import com.example.IntegrationSupportWithTruncateDb
import com.example.springboottestcodebasis.domain.comment.CommentResources
import com.example.springboottestcodebasis.domain.comment.api.CommentController
import com.example.springboottestcodebasis.domain.post.PostResources
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.hibernate.LazyInitializationException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[IntegrationSupport] PostControllerTest 는 osiv 를 꺼둔 상태이다.")
class PostControllerTest {

    @Nested
    @DisplayName("트랜잭션 처리가 최상위에 있는 통합테스트를 수행한다.")
    @IntegrationSupport
    inner class IntegrationSupportTestIncludeTx(
        private val postController: PostController,
        private val commentController: CommentController
    ) {

        @Test
        @DisplayName("Post 조회 시, Comment 도 잘 조회된다.")
        fun test() {

            // given
            val postRequest = PostResources.CreateRequest(
                title = "게시글 1",
                content = "내용 1"
            )
            val postId = postController.create(postRequest).body!!

            val commentRequest = CommentResources.CreateRequest(
                content = "댓글 1"
            )
            commentController.create(postId, commentRequest)

            // when
            val response = postController.findOneById(postId).body!!

            // then
            response.asClue {
                it.id shouldBe postId
                it.title shouldBe "게시글 1"
                it.content shouldBe "내용 1"
                it.comments.size shouldBe 1
                it.comments.first().content shouldBe "댓글 1"
            }
        }
    }

    @Nested
    @DisplayName("트랜잭션 처리가 없는 통합테스트를 수행한다. : 별도 truncate 가 수행")
    @IntegrationSupportWithTruncateDb
    inner class IntegrationSupportTestExcludeTx(
        private val postController: PostController,
        private val commentController: CommentController
    ) {

        @Test
        @DisplayName("Post 조회 시, Comment 에 대한 프록시 Lazy Exception 이 발생한다.")
        fun test() {

            // given
            val postRequest = PostResources.CreateRequest(
                title = "게시글 1",
                content = "내용 1"
            )
            val postId = postController.create(postRequest).body!!

            val commentRequest = CommentResources.CreateRequest(
                content = "댓글 1"
            )
            commentController.create(postId, commentRequest)

            // when
            val exception = assertThrows<LazyInitializationException> {
                postController.findOneById(postId)
            }

            // then
            exception.message shouldContain "could not initialize proxy - no Session"
        }
    }
}