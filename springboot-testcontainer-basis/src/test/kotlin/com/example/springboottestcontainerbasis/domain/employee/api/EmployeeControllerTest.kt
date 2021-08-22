package com.example.springboottestcontainerbasis.domain.employee.api

import com.example.springboottestcontainerbasis.IntergrationSupport
import com.example.springboottestcontainerbasis.domain.employee.resources.EmployeeCreateResources
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired

@DisplayName("EmployeeController 는")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EmployeeControllerTest : IntergrationSupport() {

    @Autowired
    private lateinit var employeeController: EmployeeController

    private val logger = KotlinLogging.logger {}

    @Test
    @DisplayName("직원을 생성한다.")
    fun createTest() {

        // given
        val request = EmployeeCreateResources(name = "park")

        // when
        val employeeId = employeeController.create(request).body

        // then
        employeeId shouldNotBe null
    }

    @Test
    @DisplayName("직원을 아이디를 통해서 찾는다.")
    fun findOneByIdTest() {

        // given
        val employeeId = employeeController.create(EmployeeCreateResources(name = "kim")).body

        // when
        val employee = employeeController.findOneById(employeeId!!).body

        // then
        employee!!.id shouldNotBe null
        employee.name shouldBe "kim"
    }
}