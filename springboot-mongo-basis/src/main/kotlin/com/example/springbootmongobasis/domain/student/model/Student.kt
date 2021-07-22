package com.example.springbootmongobasis.domain.student.model

import com.example.springbootmongobasis.domain.BaseDocument
import com.example.springbootmongobasis.domain.student.api.dto.StudentDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "student")
class Student private constructor(
    val name: String,
    val gender: Gender,
    val age: Int,
    @Indexed(unique = true)
    val email: String,
): BaseDocument() {

    @Id
    private var id: String? = null

    companion object {
        fun from(request: StudentDto.CreateRequest): Student {
            return request.run {
                Student(
                    this.name,
                    this.gender,
                    this.age,
                    this.email
                )
            }
        }
    }
}