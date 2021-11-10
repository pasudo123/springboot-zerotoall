package com.example.springboottestcodebasis.domain.member.api

import com.example.FakeDateTimeProvider
import com.example.IntegrationSupport
import com.example.springboottestcodebasis.domain.member.model.Member
import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.LocalDate
import java.time.LocalDateTime

@IntegrationSupport
@DisplayName("memberController 는")
internal class MemberControllerTest (
    private val memberController: MemberController,
    private val memberRepository: MemberRepository
) {

    companion object {
        const val DAYS = 10L
    }

    /**
     * TestConfiguration 을 별도로 해당 클래스에서 사용
     */
    @TestConfiguration
    @EnableJpaAuditing(dateTimeProviderRef = "fakeAuditingDateTimeProvider")
    class CustomTestConfiguration {
        @Bean
        fun fakeDateTimeProvider(): FakeDateTimeProvider {
            return FakeDateTimeProvider(LocalDateTime.now().minusDays(DAYS))
        }

        @Bean(name = ["fakeAuditingDateTimeProvider"])
        fun dateTimeProvider(fakeDateTimeProvider: FakeDateTimeProvider): DateTimeProvider {
            return fakeDateTimeProvider
        }
    }

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
        savedMember.createdAt!!.toLocalDate() shouldBe LocalDate.now().minusDays(DAYS)
        savedMember.modifiedAt!!.toLocalDate() shouldBe LocalDate.now().minusDays(DAYS)
    }

    @Test
    @DisplayName("멤버를 조회한다.")
    @Order(2)
    fun fetchOneByIdTest() {

        // given
        memberController.create(Member("홍길동", 10))
        val member = memberController.create(Member("손권", 33)).body!!
        memberController.create(Member("유비", 54))

        // when
        val foundMember = memberController.fetchOneById(member.id!!).body!!

        // then
        foundMember.id shouldBe member.id!!
        foundMember.name shouldBe "손권"
        foundMember.age shouldBe 33
        foundMember.createdAt!!.toLocalDate() shouldBe LocalDate.now().minusDays(DAYS)
        foundMember.modifiedAt!!.toLocalDate() shouldBe LocalDate.now().minusDays(DAYS)
    }
}