package com.example.springbootmongobasis.domain.student.repository

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StudentFinder(
    private val studentRepository: StudentRepository
) {
}