package com.example.springbootmongobasis.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.bson.types.ObjectId
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()

        // @JsonProperty 가 없어도 디폴트 생성자가 없는 객체에 @RequestBody 클래스의 필드를 매핑할 수 있다.
        // https://proandroiddev.com/parsing-optional-values-with-jackson-and-kotlin-36f6f63868ef
        objectMapper.registerModule(KotlinModule())

        // LocalDateTime 에 대한 @JsonFormat 파싱을 가능하게 한다.
        objectMapper.registerModule(JavaTimeModule())

        // ObjectId 를 응답할 때, date&timestamp 로 변환하는게 아닌 string 으로 치환할 수 있도록 한다.
        objectMapper.registerModule(object : SimpleModule() {
            init {
                addSerializer(ObjectId::class.java, ToStringSerializer())
            }
        })

        return objectMapper
    }
}
