package com.example.springbootqueryperfbasis.domain.member

import com.example.springbootqueryperfbasis.RepositorySupport
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import kotlin.system.measureTimeMillis

@RepositorySupport
@DisplayName("[select] 쿼리 속도 개선 테스트")
class MemberRepositorySelectQueryTest(
    private val entityManager: TestEntityManager,
    private val memberCustomRepository: MemberCustomRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @DisplayName("select in query 수행한다.")
    @Test
    fun selectInQueryPerfTest() {

        // given
        val uniqIds = listOf("000019ed-5b4a-4fbe-bcd5-3fc64b7418b0",
            "00001b7d-9c49-42cd-91c8-2232934025e6",
            "00001e60-e2e7-4542-a2bd-ca1359d4a5f0",
            "00002345-aa37-4296-b43e-dc0852229ae2",
            "00002549-1817-413e-951a-ab0a0e054e8d",
            "0000264b-3f6a-45ba-980b-f4a5053613e0",
            "0000305c-94f4-4aac-9a4f-5702b93cfa90",
            "00003a4d-aefc-4d87-8842-6fd3d4a9db28",
            "00004279-5eea-4689-9416-9cfd15fcc6c3",
            "000043a5-dd54-4b38-81a9-ac4f200d0bc1",
            "00004ae1-de70-4129-882b-97aa4412f6d2",
            "00004afc-1a2c-41ea-9624-d3df2ed8768d",
            "000050f0-5cc6-432d-9afa-a1941a063e23",
            "00005430-8cdc-4448-9f6d-531678e07411",
            "000054ce-0769-4d94-ae01-03f915af8ee7",
            "00005651-8934-4af7-8628-d74f93ed4c35",
            "000056e0-fa8c-47d0-b24a-b048df27336b",
            "00005727-0148-41ba-9df4-f39511c5517e",
            "000059bd-2a8b-4284-ad8f-0aefe4918eba",
            "00006727-7a49-4879-9536-2c49759828f1",
            "00006817-4c46-4d50-ab94-544eb2361f36"
        )

        val elapsed = measureTimeMillis {
            // when
            val members = memberCustomRepository.findAllByUniqIds(uniqIds)

            // then
            members.size shouldBe uniqIds.size
        }

        log.info("select in query elapsed : ${(elapsed / 1000.0)}sec")
    }
}