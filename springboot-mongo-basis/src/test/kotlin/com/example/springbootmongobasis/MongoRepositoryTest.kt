package com.example.springbootmongobasis

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@Target(AnnotationTarget.CLASS)
@SpringBootTest
@ContextConfiguration(initializers = [MongoInitializer::class])
@ActiveProfiles(profiles = ["test"])
annotation class MongoRepositoryTest {
}