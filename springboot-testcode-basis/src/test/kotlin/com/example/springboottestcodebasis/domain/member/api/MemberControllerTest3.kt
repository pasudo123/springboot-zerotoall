package com.example.springboottestcodebasis.domain.member.api

import com.example.WebLayerSupport
import com.example.springboottestcodebasis.domain.member.model.Member
import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import com.example.springboottestcodebasis.domain.member.service.MemberService
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.MockBeans
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebLayerSupport
@DisplayName("memberController3 은")
@MockBeans(value = [
    MockBean(MemberService::class),
    MockBean(MemberRepository::class),
])
class MemberControllerTest3(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val memberRepository: MemberRepository,
) {

    @Test
    @DisplayName("멤버를 생성한다.")
    @Order(1)
    fun createTest() {

        // given
        val member = Member("홍길동", 30)
        val jsonString = objectMapper.writeValueAsString(member)
        given(this.memberRepository.save(any())).willReturn(member)

        // when
        val resultAction = mockMvc.perform(post("/members")
            .content(jsonString)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("홍길동"))
            .andExpect(jsonPath("$.age").value(30))
            .andDo(MockMvcResultHandlers.print())

        // then
        val mvcResult = resultAction.andReturn()
        val responseMember = objectMapper.readValue(mvcResult. response.contentAsString, Member::class.java)
        responseMember.name shouldBe "홍길동"
        responseMember.age shouldBe 30
    }
}