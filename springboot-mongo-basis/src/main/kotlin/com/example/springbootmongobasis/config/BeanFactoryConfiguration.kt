package com.example.springbootmongobasis.config

import com.example.springbootmongobasis.util.MapperUtils
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanFactoryConfiguration {

    @Bean
    fun mapperUtils(mapper: ObjectMapper): MapperUtils {
        return MapperUtils.apply {
            this.mapper = mapper
        }
    }
}