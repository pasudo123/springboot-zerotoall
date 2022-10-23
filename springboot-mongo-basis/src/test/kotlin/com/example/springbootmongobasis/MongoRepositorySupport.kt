package com.example.springbootmongobasis

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.stereotype.Repository
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestConstructor

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DataMongoTest(
    includeFilters = [ComponentScan.Filter(type = FilterType.ANNOTATION, value = [Repository::class])],
    excludeAutoConfiguration = [EmbeddedMongoAutoConfiguration::class]
)
@ContextConfiguration(initializers = [MongoInitializer::class])
@ActiveProfiles(profiles = ["test"])
annotation class MongoRepositorySupport