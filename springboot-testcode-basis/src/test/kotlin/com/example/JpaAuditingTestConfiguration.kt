package com.example

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing
class JpaAuditingBaseConfiguration

/**
 * fakeAuditingDateTimeProvider 를 별도로 정의해서 사용할 수 있도록 한다.
 */
@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "fakeAuditingDateTimeProvider")
class JpaAuditingFakeConfiguration