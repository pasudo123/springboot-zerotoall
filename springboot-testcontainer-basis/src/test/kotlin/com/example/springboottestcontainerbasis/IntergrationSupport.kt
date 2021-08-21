package com.example.springboottestcontainerbasis

import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers

@ActiveProfiles("test")
@SpringBootTest(
    classes = [SpringbootTestcontainerBasisApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Import(value = [IntergrationTestDataSource::class])
class IntergrationSupport {

    private val logger = KotlinLogging.logger {}
}