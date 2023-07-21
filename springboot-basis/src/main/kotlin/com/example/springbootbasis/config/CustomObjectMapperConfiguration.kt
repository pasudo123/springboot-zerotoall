package com.example.springbootbasis.config

import com.example.springbootbasis.config.CustomObjectMapperConfiguration.Companion.mapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomObjectMapperConfiguration {

    companion object {
        val mapper: ObjectMapper = ObjectMapper().registerModules(
            JavaTimeModule(),
            KotlinModule(),
        ).apply {
            this.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            this.enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    
    @Bean
    fun objectMapper(): ObjectMapper {
        return mapper
    }
}

inline fun <reified T: Any> T.toJson(): String = mapper.writeValueAsString(this)