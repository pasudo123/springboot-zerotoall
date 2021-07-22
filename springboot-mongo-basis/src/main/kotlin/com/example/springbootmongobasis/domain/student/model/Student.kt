package com.example.springbootmongobasis.domain.student.model

import com.example.springbootmongobasis.domain.student.api.dto.StudentDto
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
class Student private constructor(
    val name: String,
    val gender: Gender,
    val age: Int,
    @Indexed(unique = true)
    val email: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
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