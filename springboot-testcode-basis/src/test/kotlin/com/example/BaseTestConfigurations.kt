package com.example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter
import org.springframework.boot.web.servlet.server.Encoding
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.CharacterEncodingFilter

@TestConfiguration
class TestObjectMapperConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper().registerKotlinModule()
        return mapper.registerModule(JavaTimeModule())
    }

    /**
     * MockMvc 에서 한글 인코딩 문제를 해결하기 위함.
     */
    @Bean
    fun characterEncodingFilter(): CharacterEncodingFilter {
        return OrderedCharacterEncodingFilter()
            .apply {
                encoding = Encoding.DEFAULT_CHARSET.name()
                isForceRequestEncoding = true
                isForceResponseEncoding = true
            }
    }
}