package com.example.springbootmongobasis.domain.student.api

import com.example.springbootmongobasis.domain.student.api.dto.StudentDto
import com.example.springbootmongobasis.domain.student.model.Student
import com.example.springbootmongobasis.domain.student.repository.StudentRepository
import com.example.springbootmongobasis.exception.DocumentNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    @GetMapping("{id}")
    fun findOneById(
        @PathVariable("id") id: String
    ): ResponseEntity<Student> {
        val student = studentRepository.findByIdOrNull(id)
            ?: throw DocumentNotFoundException("student 를 찾을 수 없습니다.")
        return ResponseEntity.ok(student)
    }
}