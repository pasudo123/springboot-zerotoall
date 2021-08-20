package com.example.springboottestcontainerbasis.domain.employee.repository

import com.example.springboottestcontainerbasis.domain.employee.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
}