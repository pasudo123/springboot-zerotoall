package com.example.springbootqueryperfbasis.domain.member

import com.example.springbootqueryperfbasis.RepositorySupport
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@RepositorySupport
internal class MemberRepositoryTest(
    @Autowired
    val entityManager: EntityManager
) {

    @PersistenceContext private lateinit var entityManager01: EntityManager
    @PersistenceContext private lateinit var entityManager02: EntityManager
    @PersistenceContext private lateinit var entityManager03: EntityManager

    @TestFactory
    fun entityManagerCheckTest(): List<DynamicTest> {

        return listOf(
            DynamicTest.dynamicTest("entityManager 는 프록시로 모두 동일하다.") {
                entityManager.toString() shouldBe entityManager01.toString()
                entityManager01.toString() shouldBe entityManager02.toString()
                entityManager02.toString() shouldBe entityManager03.toString()
                entityManager02.toString() shouldBe entityManager01.toString()
            },
            DynamicTest.dynamicTest("entityManager Proxy는 persist 할 때 entityManager 를 만든다.") {
                // Persist 할 시에, 프록시 SharedEntityManagerCreator.invoke() 호출. -> EntityManagerFactoryUtils.doGetTransactionalEntityManager() 이 호출
                entityManager.persist(Member("홍길동"))
                entityManager01.persist(Member("홍길동"))
                entityManager02.persist(Member("홍길동"))
                entityManager03.persist(Member("홍길동"))
            }
        )
    }

    @Test
    fun findTest() {

        // given
        val member = Member("홍길동")
        entityManager.persist(member)
        entityManager.flush()
        entityManager.clear()

        // when
        val foundMember = entityManager.find(Member::class.java, member.id!!)

        // then
        foundMember.name shouldBe "홍길동"
    }
}