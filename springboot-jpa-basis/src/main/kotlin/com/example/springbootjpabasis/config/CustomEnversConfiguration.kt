package com.example.springbootjpabasis.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.Properties
import javax.sql.DataSource

/**
 * https://docs.spring.io/spring-data/envers/docs/2.3.9.RELEASE/reference/html/#reference
 */
@Configuration
@EnableJpaRepositories(
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean::class,
    basePackages = ["com.example.springbootjpabasis"]
)
@EnableTransactionManagement
@Profile("alpha")
class CustomEnversConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter().apply {
            this.setGenerateDdl(true)
            this.setShowSql(true)
            this.setDatabase(Database.MYSQL)
        }

        /**
         * https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html
         */
        val properties = Properties().apply {
            this.setProperty("hibernate.show_sql", "true")
            this.setProperty("hibernate.format_sql", "true")
            this.setProperty("hibernate.use_sql_comments", "true")
            this.setProperty("hibernate.hbm2ddl.auto", "update")
            this.setProperty("org.hibernate.envers.revision_field_name", "rev_num")
            this.setProperty("org.hibernate.envers.audit_table_suffix", "_history")
        }

        return LocalContainerEntityManagerFactoryBean().apply {
            this.jpaVendorAdapter = vendorAdapter
            this.dataSource = dataSource()
            this.setPackagesToScan("com.example.springbootjpabasis")
//            this.setJpaProperties(properties)
        }
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager().apply {
            this.entityManagerFactory = entityManagerFactory
        }
    }
}
