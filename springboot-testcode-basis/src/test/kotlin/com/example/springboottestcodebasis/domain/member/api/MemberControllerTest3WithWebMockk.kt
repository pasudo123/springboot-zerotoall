package com.example.springboottestcodebasis.domain.member.api

import com.example.WebLayerSupport
import com.example.springboottestcodebasis.domain.member.model.Member
import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import com.example.springboottestcodebasis.domain.member.service.MemberService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.MockkBeans
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

/**
 * Mockk 을 이용한다.
 * 기존에 사용하던 Mockito 를 사용하지 않음
 */
@ExtendWith(SpringExtension::class)
@WebLayerSupport
@WebMvcTest(controllers = [MemberController::class])
@MockkBeans(value = [
    MockkBean(MemberService::class),
    MockkBean(MemberRepository::class)
])
class MemberControllerTest3WithWebMockk(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val memberRepository: MemberRepository
) {

    @Test
    @DisplayName("멤버를 생성한다.")
    @Order(1)
    fun createTest() {

        // given
        val member = Member("김철수", 50)
        val jsonString = objectMapper.writeValueAsString(member)
        every { memberRepository.save(any()) } returns member

        // when
        val resultAction = mockMvc.perform(MockMvcRequestBuilders.post("/members")
            .content(jsonString)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("김철수"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(50))
            .andDo(MockMvcResultHandlers.print())

        // then
        val mvcResult = resultAction.andReturn()
        val responseMember = objectMapper.readValue(mvcResult.response.contentAsString, Member::class.java)
        responseMember.name shouldBe "김철수"
        responseMember.age shouldBe 50
        verify { memberRepository.save(any()) }
    }
}