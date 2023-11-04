package com.example.springboot3jobrunnerbasis.config

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration

@Configuration
class CustomJobRunnerConfiguration {

    @PostConstruct
    fun init() {
    }
}