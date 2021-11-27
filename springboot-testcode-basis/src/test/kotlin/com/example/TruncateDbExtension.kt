package com.example

import mu.KLogging
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.beans.factory.getBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.metamodel.Type


/**
 * https://www.baeldung.com/junit-5-extensions
 * https://stackoverflow.com/questions/34617152/how-to-re-create-database-before-each-test-in-spring
 */
class TruncateDbExtension: BeforeEachCallback, AfterEachCallback {

    companion object: KLogging()

    override fun beforeEach(context: ExtensionContext?) {
        logger.info { "" }
        logger.info { "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" }
        logger.info { "@@@@@@@@@@@@ before each @@@@@@@@@@@@" }
        logger.info { "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" }
        logger.info { "" }
    }

    override fun afterEach(context: ExtensionContext?) {
        val applicationContext = SpringExtension.getApplicationContext(context!!)
        val entityManagerFactory = applicationContext.getBean(EntityManagerFactory::class) as EntityManagerFactory
        val entityManager = entityManagerFactory.createEntityManager()

        try {
            with(entityManager) {
                this.transaction.begin()
                this.clear()
                this.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;").executeUpdate()
                this.truncateAllTables()
                this.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;").executeUpdate()
                this.transaction.commit()
            }
        } catch (exception: Exception) {
            logger.error { "truncateDbSupport Error : ${exception.message}" }
            entityManager.transaction.rollback()
        }

        logger.info { "" }
        logger.info { "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" }
        logger.info { "@@@@@@@@@@@@ after each @@@@@@@@@@@@" }
        logger.info { "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" }
        logger.info { "" }
    }
}

fun EntityManager.truncateAllTables() {
    val tableNames = this.metamodel.entities
        .filter { entity -> entity.persistenceType == Type.PersistenceType.ENTITY  }
        .map { entity -> entity.name.replace("entity_", "") }

    tableNames.forEach { tableName ->
        this.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
    }
}