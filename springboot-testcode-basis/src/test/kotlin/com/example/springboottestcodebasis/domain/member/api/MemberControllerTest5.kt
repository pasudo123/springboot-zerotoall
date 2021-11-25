package com.example.springboottestcodebasis.domain.member.api

import com.example.MockMvcSupport
import com.example.springboottestcodebasis.domain.member.model.Member
import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@MockMvcSupport
@DisplayName("MemberController5 는")
class MemberControllerTest5(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val memberRepository: MemberRepository
) {

    @Test
    @DisplayName("멤버를 생성한다.")
    @Order(1)
    fun createTest() {

        // given
        val member = Member("강감찬", 30)
        val jsonString = objectMapper.writeValueAsString(member)

        // when
        val resultAction = mockMvc.perform(MockMvcRequestBuilders.post("/members")
            .content(jsonString)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("강감찬"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(30))
            .andDo(MockMvcResultHandlers.print())

        // then
        val members = memberRepository.findAll()
        members.size shouldBe 1
        members.first().asClue {
            it.name shouldBe "강감찬"
            it.age shouldBe 30
        }
    }
}