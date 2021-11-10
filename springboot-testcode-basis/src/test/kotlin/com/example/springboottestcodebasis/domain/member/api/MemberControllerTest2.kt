package com.example.springboottestcodebasis.domain.member.api

import com.example.IntegrationSupport
import com.example.springboottestcodebasis.domain.member.model.Member
import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import java.time.LocalDate

@IntegrationSupport
@DisplayName("memberController2 는")
class MemberControllerTest2(
    private val memberController: MemberController,
    private val memberRepository: MemberRepository
) {

    @BeforeEach
    fun init() {
        memberRepository.deleteAll()
    }

    @Test
    @DisplayName("멤버를 생성한다.")
    @Order(1)
    fun createTest() {

        // given
        val member = Member("세종대왕", 55)

        // when
        val savedMember = memberController.create(member).body!!

        // then
        savedMember.id shouldBe 1L
        savedMember.name shouldBe "세종대왕"
        savedMember.age shouldBe 55
        savedMember.createdAt!!.toLocalDate() shouldBe LocalDate.now()
        savedMember.modifiedAt!!.toLocalDate() shouldBe LocalDate.now()
    }
}