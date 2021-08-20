package com.example.springboottestcontainerbasis.domain.employee.api

import com.example.springboottestcontainerbasis.domain.employee.model.Employee
import com.example.springboottestcontainerbasis.domain.employee.repository.EmployeeRepository
import com.example.springboottestcontainerbasis.domain.employee.resources.EmployeeCreateResources
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityNotFoundException

@RestController("/employees")
class EmployeeController(
    private val employeeRepository: EmployeeRepository
) {

    @PostMapping
    fun create(
        @RequestBody request: EmployeeCreateResources
    ): ResponseEntity<Long> {
        return ResponseEntity.ok(employeeRepository.save(Employee.from(request.name)).id!!)
    }

    @GetMapping("{id}")
    fun findOneById(
        @PathVariable("id") id: Long
    ): ResponseEntity<Employee> {
        val employee = employeeRepository.findById(id).orElseThrow {
            throw EntityNotFoundException("employee not found ${id}")
        }

        return ResponseEntity.ok(employee)
    }
}