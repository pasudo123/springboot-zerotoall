package com.example.springboottestcontainerbasis.domain.employee.api

import com.example.springboottestcontainerbasis.IntergrationSupport
import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired

@DisplayName("EmployeeController Second 는")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EmployeeControllerSecondTest : IntergrationSupport() {

    @Autowired
    private lateinit var employeeController: EmployeeController

    private val logger = KotlinLogging.logger {}

    @Test
    @DisplayName("직원을 생성한다.")
    fun createTest() {
        logger.info { "직원을 생성한다.22" }
    }

    @Test
    @DisplayName("직원을 아이디를 통해서 찾는다.")
    fun findOneByIdTest() {
        logger.info { "직원을 아이디를 통해서 찾는다.22" }
    }
}