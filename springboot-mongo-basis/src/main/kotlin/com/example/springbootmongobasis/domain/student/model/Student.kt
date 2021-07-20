package com.example.springbootmongobasis.domain.student.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
class Student(
    val name: String,
    val gender: Gender,
    val age: Int,
    @Indexed(unique = true)
    val email: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Id
    private var id: String? = null
}