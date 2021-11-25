package com.example.springboottestcodebasis.domain.member.api

import com.example.IntegrationSupport
import com.example.springboottestcodebasis.domain.member.model.Member
import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import java.time.LocalDate

@IntegrationSupport
@DisplayName("memberController2 는")
class MemberControllerTest2(
    private val testRestTemplate: TestRestTemplate,
    private val memberController: MemberController,
    private val memberRepository: MemberRepository,

    @LocalServerPort
    private var port: Int
) {

    @BeforeEach
    fun init() {
        println("current port : $port")
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
        savedMember.id shouldBe 1L
        memberRepository.findAll().first().asClue {
            it.name shouldBe "세종대왕"
            it.age shouldBe 55
            it.createdAt!!.toLocalDate() shouldBe LocalDate.now()
            it.modifiedAt!!.toLocalDate() shouldBe LocalDate.now()
        }
    }

    @Test
    @DisplayName("[2] TestRestTemplate 을 통해 멤버를 생성한다.")
    @Order(2)
    fun createTestRestTemplateTest() {

        // given
        val memberRequest = Member("강감찬", 30)

        // when
        val memberResponse = testRestTemplate.postForEntity<Member>("/members", memberRequest)

        // then
        memberResponse.statusCode shouldBe HttpStatus.OK
        memberResponse.body!!.asClue {
            it.name shouldBe "강감찬"
            it.age shouldBe 30
        }
    }

    @Test
    @DisplayName("[3] 따로 데이터를 넣지 않았지만 " +
            "testRestTemplate 의 별도 스레드에서 데이터를 넣었기 때문에 롤백 동작이 안된다. " +
            "그래서 데이터는 존재한다.")
    fun findAllTest() {

        // given
        val members = memberRepository.findAll()

        // then
        members.isNotEmpty() shouldBe true
    }
}