package com.example.springboottestcodebasis.config.filter

import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomFilterConfiguration(
    private val memberRepository: MemberRepository,
    private val objectMapper: ObjectMapper
) {

    @Bean
    fun memberAuthFilter(): FilterRegistrationBean<MemberAuthFilter> {
        val registrationBean: FilterRegistrationBean<MemberAuthFilter> = FilterRegistrationBean()
        return registrationBean.apply {
            this.filter = MemberAuthFilter(objectMapper, memberRepository)
            this.addUrlPatterns("/members/*")
        }
    }
}