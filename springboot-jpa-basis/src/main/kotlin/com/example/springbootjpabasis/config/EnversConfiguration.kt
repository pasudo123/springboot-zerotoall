package com.example.springbootjpabasis.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement


/**
 * https://docs.spring.io/spring-data/envers/docs/2.3.9.RELEASE/reference/html/#reference
 */
@Configuration
@EnableJpaRepositories(
    repositoryBaseClass = EnversRevisionRepositoryFactoryBean::class
)
@EnableTransactionManagement
class EnversConfiguration