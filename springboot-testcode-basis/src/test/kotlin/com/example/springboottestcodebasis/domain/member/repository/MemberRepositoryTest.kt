package com.example.springboottestcodebasis.domain.member.repository

import com.example.RepositorySupport
import com.example.springboottestcodebasis.domain.member.model.Member
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

@RepositorySupport
@DisplayName("memberRepository 는")
class MemberRepositoryTest(
    private val memberRepository: MemberRepository,
    private val testEntityManager: TestEntityManager
) {

    @Test
    @DisplayName("저장을 수행한다.")
    fun saveTest() {

        // given
        val member = Member("홍길동", 15)

        // when
        testEntityManager.persistAndFlush(member)

        // then
        val foundMember = memberRepository.findByIdOrNull(member.id!!)!!
        foundMember.name shouldBe "홍길동"
        foundMember.age shouldBe 15
        foundMember.createdAt!!.toLocalDate() shouldBe LocalDate.now()
        foundMember.modifiedAt!!.toLocalDate() shouldBe LocalDate.now()
    }
}

@RepositorySupport
@DisplayName("memberRepository 는")
@Import
class MemberRepositoryMockTest(
    private val memberRepository: MemberRepository,
    private val testEntityManager: TestEntityManager
) {

    @Test
    @DisplayName("저장을 수행한다.")
    fun saveTest() {

        // given
        val member = Member("홍길동", 15)

        // when
        testEntityManager.persistAndFlush(member)

        // then
        val foundMember = memberRepository.findByIdOrNull(member.id!!)!!
        foundMember.name shouldBe "홍길동"
        foundMember.age shouldBe 15
        foundMember.createdAt!!.toLocalDate() shouldBe LocalDate.now()
        foundMember.modifiedAt!!.toLocalDate() shouldBe LocalDate.now()
    }
}