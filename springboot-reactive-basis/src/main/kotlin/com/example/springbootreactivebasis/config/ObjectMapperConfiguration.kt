package com.example.springbootreactivebasis.config

import com.example.springbootreactivebasis.config.ObjectMapperConfiguration.Companion.mapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        return mapper
    }

    companion object {
        val mapper = ObjectMapper().apply {

            // @JsonProperty 가 없어도 디폴트 생성자가 없는 객체에 @RequestBody 클래스의 필드를 매핑할 수 있다.
            // https://proandroiddev.com/parsing-optional-values-with-jackson-and-kotlin-36f6f63868ef
            this.registerModule(KotlinModule())

            // LocalDateTime 에 대한 @JsonFormat 파싱을 가능하게 한다.
            this.registerModule(JavaTimeModule())
        }
    }
}

inline fun <reified T: Any> T.toJson(): String = mapper.writeValueAsString(this)
inline fun <reified T: Any> String.toObject(): T = mapper.readValue(this, T::class.java)