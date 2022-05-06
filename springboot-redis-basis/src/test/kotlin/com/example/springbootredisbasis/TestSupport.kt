package com.example.springbootredisbasis

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.test.context.ActiveProfiles
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

@TestEnvironment
@DataRedisTest
annotation class RedisRepositorySupport