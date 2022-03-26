package com.example.springbootjpabasis.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean::class,
    basePackages = ["com.example.springbootjpabasis"]
)
@EnableTransactionManagement
class CustomEnversTestConfiguration {

    @Bean
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
            .url("jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE")
            .driverClassName("org.h2.Driver")
            .username("sa")
            .password("")
            .build()
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {

        val vendorAdapter = HibernateJpaVendorAdapter().apply {
                this.setGenerateDdl(true)
                this.setShowSql(true)
                this.setDatabasePlatform("org.hibernate.dialect.H2Dialect")
                this.setDatabase(Database.H2)
            }

        /**
         * https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html
         */
        val properties = Properties().apply {
            this.setProperty("hibernate.show_sql", "true")
            this.setProperty("hibernate.format_sql", "true")
            this.setProperty("hibernate.hbm2ddl.auto", "create-drop")
            this.setProperty("org.hibernate.envers.revision_field_name", "rev_num")
            this.setProperty("org.hibernate.envers.audit_table_suffix", "_history")
        }

        return LocalContainerEntityManagerFactoryBean().apply {
            this.jpaVendorAdapter = vendorAdapter
            this.dataSource = dataSource()
            this.setPackagesToScan("com.example.springbootjpabasis")
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