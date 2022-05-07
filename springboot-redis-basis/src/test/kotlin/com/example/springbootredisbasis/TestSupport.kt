package com.example.springbootredisbasis

import com.example.springbootredisbasis.config.CustomRedisTestConfiguration
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import
import org.springframework.stereotype.Repository
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestConstructor

class TestSupport

/**
 * 테스트 관련 메타 애노테이션에서 사용하기 위한 최상위 애노테이션
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
annotation class TestEnvironment

/**
 * RedisTemplate 를 wrapping 한 레파지토리 클래스(@Repository)에 대한 테스트가 가능하다.
 * - @Import / @ContextConfiguration 을 구분해서 써야함.
 */
@TestEnvironment
@DataRedisTest(
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            classes = [Repository::class]
        )
    ]
)
@ContextConfiguration(classes = [CustomRedisTestConfiguration::class])
@Import(value = [CustomRedisContainer::class])
annotation class RedisRepositorySupport