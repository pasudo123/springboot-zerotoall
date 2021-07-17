package com.example.springbootjpabasis.config

import com.example.springbootjpabasis.domain.book.repository.BookRepository
import com.example.springbootjpabasis.domain.library.repository.LibraryRepository
import com.example.springbootjpabasis.domain.post.repository.PostRepository
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
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


/**
 * https://docs.spring.io/spring-data/envers/docs/2.3.9.RELEASE/reference/html/#reference
 */
@Configuration
@EnableJpaRepositories(
    repositoryBaseClass = EnversRevisionRepositoryFactoryBean::class,
    basePackageClasses = [BookRepository::class, LibraryRepository::class, PostRepository::class]
)
@EnableTransactionManagement
class CustomEnversConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {

        val vendorAdapter = HibernateJpaVendorAdapter()
            .apply {
                this.setGenerateDdl(true)
                this.setShowSql(true)
                this.setDatabasePlatform("org.hibernate.dialect.MySQL57Dialect")
                this.setDatabase(Database.MYSQL)
            }

        return LocalContainerEntityManagerFactoryBean().apply {
            this.jpaVendorAdapter = vendorAdapter
            this.setPackagesToScan("com.example.springbootjpabasis")
            this.dataSource = dataSource()
            afterPropertiesSet()
        }
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager().apply {
            this.entityManagerFactory = entityManagerFactory
            this.afterPropertiesSet()
        }
    }
}