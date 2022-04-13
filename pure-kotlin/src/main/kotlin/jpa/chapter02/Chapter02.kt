package jpa.chapter02

import jpa.Member
import javax.persistence.EntityManager
import javax.persistence.Persistence

class Chapter02 {
}

fun main() {

    // 엔티티 매니저 팩토리 생성
    println("1. 엔티티 매니저 팩토리 생성, createEntityManagerFactory 수행 시 설정파일을 읽어들임")
    val entityManagerFactory = Persistence.createEntityManagerFactory("jpabook")

    // 엔티티 매니저 생성
    println("2. 엔티티 매니저 생성")
    val entityManager = entityManagerFactory.createEntityManager()

    // 트랜잭션 획득
    println("3. 트랜잭션 획득")
    val tx = entityManager.transaction

    try {

        tx.begin()
        businessLogin(entityManager)
        tx.commit()
    } catch (exception: Exception) {
        println("rollback ! : ${exception.message}")
        tx.rollback()
    } finally {
        println("finally")
        entityManager.close()
    }

    entityManagerFactory.close()
}

private fun businessLogin(em: EntityManager) {

    val member = Member().apply {
        this.name = "PARK"
        this.age = 10
    }

    em.persist(member)
}