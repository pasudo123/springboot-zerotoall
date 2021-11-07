package com.example

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing
class JpaAuditingBaseConfiguration

@Configuration
@EnableJpaAuditing
class JpaAuditingFakeConfiguration {

}