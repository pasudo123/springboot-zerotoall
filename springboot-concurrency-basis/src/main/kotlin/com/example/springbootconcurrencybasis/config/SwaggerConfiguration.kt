package com.example.springbootconcurrencybasis.domain.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    fun restAPI(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.springbootconcurrencybasis"))
            .paths(PathSelectors.any())
            .build()
    }

    fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("SpringBoot REST API")
            .version("1.0.0")
            .description("스프링부트 Rest api 명세입니다.")
            .build()
    }
}