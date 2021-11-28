package com.example.springboottestcodebasis.domain.post.api

import com.example.MockMvcSupport
import com.example.springboottestcodebasis.domain.comment.CommentResources
import com.example.springboottestcodebasis.domain.post.PostResources
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestFactory
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@DisplayName("[MockMvcSupport] PostController 는 osiv 를 꺼둔 상태이다.")
class PostControllerTest2 {

    @Nested
    @DisplayName("트랜잭션 처리가 최상위에 있는 mockMvc 테스르를 수행한다. : lazyException 발생")
    @MockMvcSupport
    inner class IntegrationSupportTestIncludeTx(
        private val mockMvc: MockMvc,
        private val objectMapper: ObjectMapper,
    ) {

        @TestFactory
        @DisplayName("Post 조회 시, Comment 에 대한 프록시 Lazy Exception 이 발생한다.")
        fun test(): List<DynamicTest> {

            var postId = 0L

            return listOf(
                DynamicTest.dynamicTest("게시글을 저장한다.") {

                    // given
                    val postRequest = PostResources.CreateRequest(
                        title = "게시글 1",
                        content = "내용 1"
                    )
                    val jsonString = objectMapper.writeValueAsString(postRequest)

                    // when
                    val result = mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                    )

                    // then
                        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn()

                    postId = result.response.contentAsString.toLong()
                    postId shouldNotBe null
                },
                DynamicTest.dynamicTest("특정 게시글에 댓글을 저장한다.") {

                    // given
                    val commentRequest = CommentResources.CreateRequest(
                        content = "댓글 1"
                    )
                    val jsonString = objectMapper.writeValueAsString(commentRequest)

                    // when
                    mockMvc.perform(MockMvcRequestBuilders.post("/posts/$postId/comments")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                    )

                    // then
                        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn()
                },
                DynamicTest.dynamicTest("특정 게시글을 댓글과 함께 조회한다.") {

                    // when
                    mockMvc.perform(MockMvcRequestBuilders.get("/posts/$postId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                    )

                    // then
                        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn()
                }
            )
        }
    }
}