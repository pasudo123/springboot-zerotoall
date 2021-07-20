package com.example.springbootmongobasis.domain.student.repository

import com.example.springbootmongobasis.domain.student.model.Student
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : MongoRepository<Student, String> {
}