package com.example.springboottestcodebasis.domain.member.api

import com.example.SimpleMockSupport
import com.example.springboottestcodebasis.domain.member.model.Member
import com.example.springboottestcodebasis.domain.member.model.MockMember
import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import com.example.springboottestcodebasis.domain.member.service.MemberService
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

@SimpleMockSupport
@DisplayName("MemberController4 는")
class MemberControllerTest4 {

    private val memberService: MemberService = mockk()
    private val memberRepository: MemberRepository = mockk()
    private val memberController: MemberController = MemberController(
        this.memberService,
        this.memberRepository
    )

    @Test
    @DisplayName("멤버를 생성한다.")
    @Order(1)
    fun createTest() {

        // given
        val member = Member("김아무개", 20)
        every { memberRepository.save(any()) } returns
                MockMember.createMock(id = 1L, name = "김아무개", age = 20)

        // when
        val savedMember = this.memberController.create(member).body!!

        // then
        savedMember.id shouldBe 1
        savedMember.name shouldBe "김아무개"
        savedMember.age shouldBe 20
        savedMember.createdAt shouldNotBe null
        savedMember.modifiedAt shouldNotBe null
    }
}