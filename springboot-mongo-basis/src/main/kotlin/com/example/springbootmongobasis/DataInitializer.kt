package com.example.springbootmongobasis

import com.example.springbootmongobasis.domain.student.model.Gender
import com.example.springbootmongobasis.domain.student.model.Student
import com.example.springbootmongobasis.domain.student.repository.StudentRepository
import com.example.springbootmongobasis.util.toJsonString
import mu.KLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val studentRepository: StudentRepository
) : CommandLineRunner{

    companion object : KLogging()

    override fun run(vararg args: String?) {
        val student = Student(
            name = "pasudo123",
            gender = Gender.MALE,
            email = "pasudo123@naver.com",
            age = 30
        )
        studentRepository.save(student)
        logger.info { "===================================" }
        logger.info { "student : ${student.toJsonString()}" }
    }
}