package com.example.springbootswaggerbasis

import com.fasterxml.classmate.TypeResolver
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityScheme
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@SpringBootApplication
class SpringbootSwaggerBasisApplication

fun main(args: Array<String>) {
    runApplication<SpringbootSwaggerBasisApplication>(*args)
}

@Configuration
class SwaggerConfiguration(
    private val typeResolver: TypeResolver
) {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
            .registerKotlinModule()
            .registerModule(JavaTimeModule())
    }

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            // 타입 string($date-time) 을 string 으로 바꿈.
            .directModelSubstitute(LocalDate::class.java, String::class.java)
            .directModelSubstitute(LocalDateTime::class.java, String::class.java)
            .directModelSubstitute(LocalTime::class.java, String::class.java)
            // 스웨거 도큐먼트에 별도의 모델을 하나 더 추가한다. 딱히 어디에 쓰이는지 잘 모르겠는데, 에러 발생할 경우 필드 설명을 볼 용도르 쓰는듯?
            .additionalModels(typeResolver.resolve(Dummy::class.java))
            .apiInfo(apiInfo())
            .groupName("스프링부트 스웨서 학습 프로젝트 GROUP")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.springbootswaggerbasis"))

            // paths 는 모두 등록
            // "/teams" path 로 시작되는 경로는 제외
            .paths(PathSelectors.any()
                .and(PathSelectors.ant("/teams").negate())
            )
            .build()

            // auth 관련
            .securitySchemes(listOf(
                ApiKey("user-header", "유저헤더", "authorize"),
                ApiKey("user-header", "유저헤더", "authorize"),
            ))
    }

    fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("스웨거 작성을 위한 부트 프로젝트")
            .description("예시 api 목록입니다.")
            .version("1.0")
            .contact(Contact("pasudo123", "https://github.com/pasudo123", "pasudo123@naver.com"))
            .build()
    }
}

class Dummy(
    val name: String
)

@Configuration
@EnableWebMvc
class WebConfiguration: WebMvcConfigurer {

    /**
     * swagger-ui -> swagger-ui/index.html 로 리다이렉트 한다.
     */
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/swagger-ui", "/swagger-ui/index.html")
    }
    /**
     * swagger-ui 를 리소스로 등록한다.
     */
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
