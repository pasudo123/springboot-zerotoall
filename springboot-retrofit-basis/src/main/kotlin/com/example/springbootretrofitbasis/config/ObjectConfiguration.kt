package com.example.springbootretrofitbasis.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectConfiguration {

    companion object {
        private val mapper = ObjectMapper().apply {
            this.registerModule(KotlinModule())
            this.registerModule(JavaTimeModule())
        }
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return mapper
    }
}