package com.example.springbootwasbasis.config.web

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class GlobalWebConfiguration(
    private val pathTrackerInterceptor: PathTrackerInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(pathTrackerInterceptor)
    }
}