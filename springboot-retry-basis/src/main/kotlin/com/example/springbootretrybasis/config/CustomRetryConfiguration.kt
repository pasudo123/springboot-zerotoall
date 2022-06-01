package com.example.springbootretrybasis.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.backoff.FixedBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate

@Configuration
@EnableRetry
class CustomRetryConfiguration {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun retryTemplate(): RetryTemplate {
        val fixedBackOffPolicy = FixedBackOffPolicy().apply {
            this.backOffPeriod = 3000L
        }

        val retryPolicy = SimpleRetryPolicy().apply {
            this.maxAttempts = 2
        }

        val retryTemplate = RetryTemplate().apply {
            this.setBackOffPolicy(fixedBackOffPolicy)
            this.setRetryPolicy(retryPolicy)
            this.setListeners(arrayOf(DefaultListenerSupport()))
        }

        return retryTemplate
    }

    @Bean
    fun defaultListenerSupport(): DefaultListenerSupport {
        return DefaultListenerSupport()
    }
}