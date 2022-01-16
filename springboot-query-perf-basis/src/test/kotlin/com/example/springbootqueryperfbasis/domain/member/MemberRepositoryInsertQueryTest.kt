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
         *  hibernate.jdbc.batch_size: 5   로 했을 때 소요시간 :: 27.024sec
         *  hibernate.jdbc.batch_size: 10  로 했을 때 소요시간 :: 27.024sec
         *  hibernate.jdbc.batch_size: 50  로 했을 때 소요시간 :: 31.009sec
         *  hibernate.jdbc.batch_size: 100 로 했을 때 소요시간 :: 34.017sec
         *  hibernate.jdbc.batch_size: 200 로 했을 때 소요시간 :: 26.737sec
         *
         * **** @@@중요@@@ ***
         * - 사실 위에 내용은 소용없음
         * @GeneratedValue(strategy = GenerationType.IDENTITY) 로 선언된 아이디의 경우에는 batch_size 가 작동하지 않는다.
         * 결국 위에 테스트는 무의미
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