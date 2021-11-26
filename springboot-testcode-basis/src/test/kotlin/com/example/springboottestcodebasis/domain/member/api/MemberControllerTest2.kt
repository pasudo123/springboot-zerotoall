package com.example.springboottestcodebasis.domain.member.api

import com.example.IntegrationSupport
import com.example.springboottestcodebasis.constant.Constant
import com.example.springboottestcodebasis.domain.member.model.Member
import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.util.LinkedMultiValueMap
import java.time.LocalDate


@IntegrationSupport
@DisplayName("memberController2 는")
class MemberControllerTest2(
    private val objectMapper: ObjectMapper,
    private val testRestTemplate: TestRestTemplate,
    private val memberController: MemberController,
    private val memberRepository: MemberRepository,

    @LocalServerPort
    private var port: Int,
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

        // 아래와 같이 서블릿 컨테이너가 실행된다. (테스트 컨텍스트의 메인 스레드가 아닌 별도 스레드에서 동작)
        // 2021-11-25 21:59:38.158  INFO 63375 --- [o-auto-1-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
        // 2021-11-25 21:59:38.158  INFO 63375 --- [o-auto-1-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
        // 2021-11-25 21:59:38.174  INFO 63375 --- [o-auto-1-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 16 ms

        // given
        val member = Member("강감찬", 30)

        // when
        val headers = LinkedMultiValueMap<String, String>().apply{
            this.add("Content-Type", "application/json")
            this.add(Constant.PermissionHeader.KEY, Constant.PermissionHeader.ADMIN.VALUE)
        }
        val request: HttpEntity<String> = HttpEntity(objectMapper.writeValueAsString(member), headers)
        val memberResponse = testRestTemplate
            .postForEntity<Member>("/members", request, Member::class)

        // then
        memberResponse.statusCode shouldBe HttpStatus.OK
        memberResponse.body!!.asClue {
            it.name shouldBe "강감찬"
            it.age shouldBe 30
        }
    }

    @Test
    @DisplayName("[3] TestRestTemplate 을 통해 멤버를 생성하지만, 헤더가 없는 문제로 권한 에러가 발생한다.")
    @Order(3)
    fun createTestRestTemplateThrowTest() {

        // given
        val memberRequest = Member("루피", 18)

        // when
        val exception = testRestTemplate
            .postForEntity<String>("/members", memberRequest)

        // then
        exception.statusCode shouldBe HttpStatus.INTERNAL_SERVER_ERROR
    }

    @Test
    @DisplayName("[100] 따로 데이터를 넣지 않았지만 " +
            "testRestTemplate 의 별도 스레드에서 데이터를 넣었기 때문에 롤백 동작이 안된다. " +
            "그래서 데이터는 존재한다.")
    @Order(100)
    fun findAllTest() {

        // given
        val members = memberRepository.findAll()

        // then
        members.isNotEmpty() shouldBe true
        members.size shouldBe 1
        members.first().asClue {
            it.name shouldBe "강감찬"
            it.age shouldBe 30
        }
    }
}