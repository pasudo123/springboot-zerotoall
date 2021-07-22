package com.example.springbootmongobasis.domain.student.api

import com.example.springbootmongobasis.domain.student.api.dto.StudentDto
import com.example.springbootmongobasis.domain.student.model.Student
import com.example.springbootmongobasis.domain.student.repository.StudentRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("students")
class StudentController(
    private val studentRepository: StudentRepository
) {

    @PostMapping
    fun create(
        @RequestBody request: StudentDto.CreateRequest
    ): ResponseEntity<Student> {
        val student = Student.from(request)
        return ResponseEntity.ok(studentRepository.save(student))
    }


}