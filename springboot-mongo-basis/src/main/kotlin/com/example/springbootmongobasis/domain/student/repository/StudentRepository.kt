package com.example.springbootmongobasis.domain.student.repository

import com.example.springbootmongobasis.domain.student.model.Gender
import com.example.springbootmongobasis.domain.student.model.Student
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StudentRepository : MongoRepository<Student, String> {

    fun findStudentByEmail(email: String): Optional<Student>

    fun findStudentsByGender(gender: Gender): List<Student>
}