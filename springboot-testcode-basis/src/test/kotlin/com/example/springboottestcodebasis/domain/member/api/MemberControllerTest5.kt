package com.example.springboottestcodebasis.domain.member.api

import com.example.MockMvcSupport
import com.example.springboottestcodebasis.constant.Constant
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
    @DisplayName("[1] 멤버를 생성한다.")
    @Order(1)
    fun createTest() {

        // given
        val member = Member("강감찬", 30)
        val jsonString = objectMapper.writeValueAsString(member)

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/members")
            .content(jsonString)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header(Constant.PermissionHeader.KEY, Constant.PermissionHeader.ADMIN.VALUE)
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

    @Test
    @DisplayName("[2] 멤버를 생성하려고 하지만 헤더 권한이 없어서 에러가 발생한다.")
    @Order(2)
    fun createThrowTest() {

        // given
        val member = Member("강감찬", 30)
        val jsonString = objectMapper.writeValueAsString(member)

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/members")
            .content(jsonString)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )

        // then
            .andExpect(MockMvcResultMatchers.status().is5xxServerError)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("[100] 데이터를 조회하지만 서블릿이 mock 이기 때문에 롤백이 정상동작, 데이터는 없다.")
    @Order(100)
    fun findAllTest() {

        // when
        val members = memberRepository.findAll()

        // then
        members.size shouldBe 0
    }
}