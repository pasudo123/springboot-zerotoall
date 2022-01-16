package com.example.springbootqueryperfbasis.domain.member

import com.example.springbootqueryperfbasis.RepositorySupport
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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

    @DisplayName("jpa saveAll() 을 수행한다.")
    @Test
    @Commit
    fun saveQueryPerfTest() {
        val memberGroup = mutableListOf<Member>()

        repeat(100000) {
            memberGroup.add(MemberFixture.aEntity())
        }

        val elapsed = measureTimeMillis {
            memberRepository.saveAll(memberGroup)
            entityManager.flush()
            entityManager.clear()
        }

        log.info("elapsed : ${(elapsed / 1000.0)}sec")

        /**
         * 10만건 넣는것 기준
         *  hibernate.jdbc.batch_size: 5   로 했을 때 소요시간 :: 27.024sec
         *  hibernate.jdbc.batch_size: 50  로 했을 때 소요시간 :: 31.009sec
         *  hibernate.jdbc.batch_size: 100 로 했을 때 소요시간 :: 34.017sec
         *  hibernate.jdbc.batch_size: 200 로 했을 때 소요시간 :: 26.737sec (rewriteBatchedStatements=true 옵션이 없는 경우)
         *  hibernate.jdbc.batch_size: 200 로 했을 때 소요시간 :: 36.737sec (rewriteBatchedStatements=true 옵션이 있는 경우)
         */
    }
}