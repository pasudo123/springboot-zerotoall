package com.example.springboottestcodebasis.domain.member.api

import com.example.WebLayerSupport
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc

@WebLayerSupport
@DisplayName("memberController3 은")
class MemberControllerTest3(
   private val mockMvc: MockMvc
) {

    companion object {
        const val contentType = "application/json"
    }

    @Test
    @DisplayName("멤버를 생성한다.")
    @Order(1)
    fun createTest() {

//        // given
//        mockMvc.perform(
//            post("members")
//                .contentType(contentType)
//        )
//                // when
//                // then
//            .andExpect()
    }
}