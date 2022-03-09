package com.example.springboottestcodebasis.domain.member.api

import com.example.IntegrationSupportWithTestContainers
import com.example.springboottestcodebasis.domain.member.model.Member
import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import mu.KLogging
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import java.time.LocalDate

@IntegrationSupportWithTestContainers
@DisplayName("MemberControllerTest7 는")
class MemberControllerTest7WithTestContainer(
    private val memberController: MemberController,
    private val memberRepository: MemberRepository,
) {

    companion object: KLogging()

    @BeforeEach
    fun beforeEach() {
        logger.info { "##### [beforeEach] new new new new #####" }
    }

    @Test
    @DisplayName("[1] Controller 클래스를 통해 멤버를 생성한다.")
    @Order(1)
    fun createTest() {

        // given
        val member = Member("세종대왕", 55)

        // when
        val savedMember = memberController.create(member).body!!

        // then
        savedMember.id shouldNotBe null
        memberRepository.findAll()
            .find { currentMember -> currentMember.id == savedMember.id }!!
            .asClue {
                it.name shouldBe "세종대왕"
                it.age shouldBe 55
                it.createdAt!!.toLocalDate() shouldBe LocalDate.now()
                it.modifiedAt!!.toLocalDate() shouldBe LocalDate.now()
            }
    }

    @Test
    @DisplayName("[100] 데이터는 테스트 라이프사이클에 의해 truncate 되어 존재하지 않는다.")
    @Order(100)
    fun findAllTest() {

        // given
        val members = memberRepository.findAll()

        // then
        members.isEmpty() shouldBe true
        members.size shouldBe 0
    }

    @AfterEach
    fun afterEach() {
        logger.info { "##### [afterEach] new new new new #####" }
    }
}