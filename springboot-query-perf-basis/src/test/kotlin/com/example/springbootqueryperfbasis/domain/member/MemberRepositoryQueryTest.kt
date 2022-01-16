package com.example.springbootqueryperfbasis.domain.member

import com.example.springbootqueryperfbasis.RepositorySupport
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.annotation.Commit
import kotlin.system.measureTimeMillis

@RepositorySupport
@DisplayName("쿼리 속도 개선 테스트")
class MemberRepositoryQueryTest(
    private val entityManager: TestEntityManager,
    private val memberRepository: MemberRepository,
    private val memberCustomRepository: MemberCustomRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @TestFactory
    @Commit
    fun saveQueryPerfTest(): List<DynamicTest> {
        val memberAGroup = mutableListOf<Member>()
        val memberBGroup = mutableListOf<Member>()

        repeat(1000000) {
            memberAGroup.add(MemberFixture.aEntity())
            memberBGroup.add(MemberFixture.aEntity())
        }

        return listOf(
            DynamicTest.dynamicTest("jpa saveAll() : insert line by line 을 수행한다.") {
                val elapsed = measureTimeMillis {
                    memberRepository.saveAll(memberAGroup)
                    entityManager.flush()
                    entityManager.clear()
                }

                log.info("elapsed : ${(elapsed / 1000.0)}sec")
            },
            DynamicTest.dynamicTest("insert multi line 을 수행한다.") {

            }
        )
    }
}