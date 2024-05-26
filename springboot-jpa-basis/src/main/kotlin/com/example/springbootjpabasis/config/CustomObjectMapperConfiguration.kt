package com.example.springbootjpabasis.config

import com.example.springbootjpabasis.config.CustomObjectMapperConfiguration.Companion.mapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomObjectMapperConfiguration {

    companion object {
        val mapper = ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(JavaTimeModule())
            .registerKotlinModule()
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return mapper
    }
}

inline fun <reified T : Any> T.toJson(): String = mapper.writeValueAsString(this)
inline fun <reified T : Any> String.toObject(): T = mapper.readValue(this, T::class.java)
inline fun <reified T : Any> String?.toObjectList(): List<T> =
    this?.let { mapper.readerForListOf(T::class.java).readValue(it) } ?: emptyList()