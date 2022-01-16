package com.example.springbootqueryperfbasis.domain.member

import com.example.springbootqueryperfbasis.RepositorySupport
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.Commit
import java.sql.PreparedStatement
import kotlin.system.measureTimeMillis

@RepositorySupport
@DisplayName("[insert] 쿼리 속도 개선 테스트")
class MemberRepositoryInsertQueryTest(
    private val entityManager: TestEntityManager,
    private val memberRepository: MemberRepository,
    private val jdbcTemplate: JdbcTemplate
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @DisplayName("jpa saveAll() 을 수행한다.")
    @Test
    @Commit
    fun saveQueryPerfTest() {
        val memberGroup = mutableListOf<Member>()

        repeat(100) {
            memberGroup.add(MemberFixture.aEntity())
        }

        val elapsed = measureTimeMillis {
            memberRepository.saveAll(memberGroup)
            entityManager.flush()
            entityManager.clear()
        }

        log.info("jpa elapsed : ${(elapsed / 1000.0)}sec")

        /**
         * [100건] 넣는것 기준
         *  - 0.252sec
         *
         * [10만건] 넣는것 기준
         *  - 27.024sec
         *  - 27.024sec
         *  - 31.009sec
         *  - 34.017sec
         *  - 26.737sec
         *
         * **** @@@중요@@@ ***
         * 엔티티의 아이디가 @GeneratedValue(strategy = GenerationType.IDENTITY) 로 선언된 경우에는 batch_size 가 작동하지 않는다.
         * 그래서 jdbcTemplate 을 이용한다.
         */
    }

    @DisplayName("jdbcTemplate 을 이용해 저장한다.")
    @Test
    @Commit
    fun saveQueryPerfTestByJdbcTemplate() {

        val memberGroup = mutableListOf<Member>()

        val insertQuery = "INSERT INTO member (member_uniq_id, name) VALUES (?, ?)"
        log.info("query ==> $insertQuery")


        jdbcTemplate.apply {
            this.queryTimeout = 1500
        }

        val elapsed = measureTimeMillis {

            repeat(1) {
                repeat(100) {
                    memberGroup.add(MemberFixture.aEntity())
                }

                // INSERT INTO member (member_uniq_id, name) VALUES ('45c3a70b-de09-4ee1-9038-68798c386f01','홍길동858892451'),('97577df1-6517-47df-8758-52310e17d6f3','홍길동843203773')
                // mysql 로그로 보면 멀티라인으로 찍힌다.
                jdbcTemplate.batchUpdate(insertQuery, object : BatchPreparedStatementSetter {
                    override fun setValues(ps: PreparedStatement, i: Int) {
                        ps.setString(1, memberGroup[i].memberUniqId)
                        ps.setString(2, memberGroup[i].name)
                    }

                    override fun getBatchSize(): Int {
                        // batchSize 를 임의로 설정하면 setValues() 가 size 만큼 호출되니 유의한다.
                        return memberGroup.size
                    }
                })

                log.info("execute batchInsert ==> size : ${memberGroup.size}")
                memberGroup.clear()
            }
        }

        log.info("jdbcTemplate elapsed : ${(elapsed / 1000.0)}sec")

        /**
         * 100건
         *  - 0.022sec
         *
         * 10만건
         *  - 5.811sec 소요 (batch size : 10만)
         *  - 5.904sec 소요 (batch size : 10만)
         *  - 14.586sec 소요 (batch size : 1만)
         *
         * 100만건
         *  - 117.841sec (batch size : 10만)
         */
    }
}