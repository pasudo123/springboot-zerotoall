package com.example.springbootbasis.config

import com.example.springbootbasis.config.interceptor.AccountLoggingInterceptor
import com.example.springbootbasis.config.interceptor.AccountLoggingWebInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CustomMvcConfiguration(
    private val accountLoggingInterceptor: AccountLoggingInterceptor,
    private val accountLoggingWebInterceptor: AccountLoggingWebInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(accountLoggingInterceptor)
            .addPathPatterns("/accounts/**")

        registry.addWebRequestInterceptor(accountLoggingWebInterceptor)
            .addPathPatterns("/accounts/**")
    }
}