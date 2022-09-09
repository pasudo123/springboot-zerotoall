package com.example.springbootjdbcbasis.domain.person

import com.example.springbootjdbcbasis.JdbcTemplateTestSupport
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.Rollback
import java.util.UUID
import kotlin.system.measureTimeMillis

/**
 * 5000건 기준
 * 4초/8초/12초
 *
 * 100000건 기준
 * 92초/205초/297초
 */
@JdbcTemplateTestSupport
@Rollback(value = false)
internal class PersonRepositoryTest(
    private val jdbcTemplate: JdbcTemplate
) {

    private val personRepository: PersonRepository = PersonRepository(jdbcTemplate)
    private val log = LoggerFactory.getLogger(javaClass)
    private val persons: MutableList<Person> = mutableListOf()

    @BeforeEach
    fun init() {
        log.info("==== init() ====")

        repeat(100000) {
            val name = "name${UUID.randomUUID().toString().subSequence(0, 15)}"
            val email = "${name}@gmail.com"
            val remark = "ss"

            persons.add(Person(name, email, remark))
        }
    }

    @Test
    @DisplayName("저장 성능 테스트")
    @Order(1)
    fun insertPerformanceTest() {
        log.info(":: 저장 수행 시작")

        val elapsed = measureTimeMillis {
            personRepository.insertPersonBulk(persons)
        }

        log.info(":: 저장 수행 완료 ==> ${elapsed / 1000}sec")
    }
    
    @Test
    @DisplayName("키 충돌 -> 업데이트 성능 테스트")
    @Order(2)
    fun upsertPerformanceTest() {
        personRepository.insertPersonBulk(persons)
        persons.forEachIndexed { index, person ->
            person.remark = "testtest${index}"
        }

        log.info(":: 저장 수행 시작")
 
        val elapsed = measureTimeMillis {
            personRepository.upsertPersonBulk(persons)
        }

        log.info(":: 저장 수행 완료 ==> ${elapsed / 1000}sec")
    }
    
    @Test
    @DisplayName("수정 성능 테스트")
    @Order(3)
    fun updatePerformanceTest() {
        personRepository.insertPersonBulk(persons)
        persons.forEachIndexed { index, person ->
            person.remark = "testtest${index}"
        }

        log.info(":: 저장 수행 시작")

        val elapsed = measureTimeMillis {
            personRepository.updatePersonBulk(persons)
        }

        log.info(":: 저장 수행 완료 ==> ${elapsed / 1000}sec")
    }

    @AfterEach
    fun destroy() {
        log.info("==== destroy() ====")

        personRepository.deleteAll()
    }
}