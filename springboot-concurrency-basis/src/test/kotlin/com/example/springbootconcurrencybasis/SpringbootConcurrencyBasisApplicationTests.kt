package com.example.springbootconcurrencybasis

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(locations = ["classpath:application.yml"])
class SpringbootConcurrencyBasisApplicationTests {

    @Test
    fun contextLoads() {
    }
}
