package com.example.springboottestcontainerbasis.domain.employee.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "employee")
class Employee private constructor (
    @Column(name = "name")
    val name: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun from(name: String): Employee {
            return Employee(name)
        }
    }
}