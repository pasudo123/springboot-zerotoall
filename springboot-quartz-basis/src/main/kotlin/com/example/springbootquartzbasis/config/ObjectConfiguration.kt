package com.example.springbootquartzbasis.config

import com.example.springbootquartzbasis.config.ObjectConfiguration.Companion.mapper
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectConfiguration {

    companion object {
        val mapper = ObjectMapper()
            .registerKotlinModule()
    }
}

inline fun <reified T: Any> T.toJson(): String = mapper.writeValueAsString(this)
inline fun <reified T: Any> String.toObject(): T = mapper.readValue(this, T::class.java)
inline fun <reified T: Any> String.toList(): List<T> = mapper.readValue(this, object: TypeReference<List<T>>() {})