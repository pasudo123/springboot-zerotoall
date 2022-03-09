package com.example

import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@TestConfiguration
class BaseTestContainerConfiguration {

    companion object {
        @JvmStatic
        @Container
        val MYSQL_CONTAINER = MysqlTestContainer.SpecifiedMySQLContainer(image = "mysql:8.0.26")
            .apply { withDatabaseName("testdb") }
            .apply { withUsername("testroot") }
            .apply { withPassword("testrootpass") }
            .apply { withUrlParam("useSSL", "false") }
            .apply { withUrlParam("allowPublicKeyRetrieval", "true") }
            .apply { withUrlParam("useTimezone", "true") }
            .apply { withUrlParam("serverTimezone", "Asia/Seoul") }
            .apply { withCommand("--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci") }
            .apply { start() }
    }

    @Component(value = "MysqlTestContainer")
    @Testcontainers
    class MysqlTestContainer {

        private val logger = KotlinLogging.logger {}

        @PostConstruct
        fun init() {
            MYSQL_CONTAINER.isRunning shouldBe true
            logger.info { "======== [started] mysql container ========" }
            logger.info { "======== imageName : ${MYSQL_CONTAINER.dockerImageName}" }
            logger.info { "======== databaseName : ${MYSQL_CONTAINER.databaseName}" }
            logger.info { "======== url : ${MYSQL_CONTAINER.jdbcUrl}" }
            logger.info { "======== username : ${MYSQL_CONTAINER.username}" }
            logger.info { "======== password : ${MYSQL_CONTAINER.password}" }
            logger.info { "======== exposedPorts : ${MYSQL_CONTAINER.exposedPorts}" }
            logger.info { "======== boundPortNumbers : ${MYSQL_CONTAINER.boundPortNumbers}" }
            logger.info { "==========================================" }
        }

        @PreDestroy
        fun beforeDestroy() {
            MYSQL_CONTAINER.stop()
            logger.info { "======== [stopped] mysql container ========" }
            logger.info { "===========================================" }
        }

        // https://github.com/testcontainers/testcontainers-java/issues/318
        class SpecifiedMySQLContainer(image: String) : MySQLContainer<SpecifiedMySQLContainer>(image)
    }

    @TestConfiguration
    class PreConditionConfiguration {

        @Bean
        @DependsOn("MysqlTestContainer")
        fun dataSource(): DataSource {
            return DataSourceBuilder.create()
                .url(MYSQL_CONTAINER.jdbcUrl)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username(MYSQL_CONTAINER.username)
                .password(MYSQL_CONTAINER.password)
                .build()
        }

        @Bean
        fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {

            val vendorAdapter = HibernateJpaVendorAdapter().apply {
                this.setGenerateDdl(true)
                this.setShowSql(true)
                this.setDatabase(Database.MYSQL)
            }

            val properties = Properties().apply {
                this.setProperty("hibernate.show_sql", "true")
                this.setProperty("hibernate.format_sql", "true")
                this.setProperty("hibernate.hbm2ddl.auto", "create-drop")
                this.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
            }

            return LocalContainerEntityManagerFactoryBean().apply {
                this.jpaVendorAdapter = vendorAdapter
                this.dataSource = dataSource()
                this.setPackagesToScan("com.example")
                this.setJpaProperties(properties)
            }
        }

        @Bean
        fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
            return JpaTransactionManager().apply {
                this.entityManagerFactory = entityManagerFactory
            }
        }
    }
}