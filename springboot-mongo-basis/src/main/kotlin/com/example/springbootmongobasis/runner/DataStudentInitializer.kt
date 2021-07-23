package com.example.springbootmongobasis.runner

import com.example.springbootmongobasis.domain.student.api.dto.StudentDto
import com.example.springbootmongobasis.domain.student.model.Gender
import com.example.springbootmongobasis.domain.student.model.Student
import com.example.springbootmongobasis.domain.student.repository.StudentRepository
import com.example.springbootmongobasis.util.toJsonString
import com.example.springbootmongobasis.util.toLineString
import mu.KLogging
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import java.util.*
import kotlin.random.Random

@Component
class DataStudentInitializer(
    private val studentRepository: StudentRepository,
    private val mongoTemplate: MongoTemplate
) {

    companion object : KLogging()

    fun process() {
        deleteAll()
        val student = insert()
        findOneByEmailUseQuery(student.email)
        findAllByGenderUseNamedQuery()
    }

    private fun deleteAll() {
        studentRepository.deleteAll()
    }

    private fun insert(): Student {
        val name = UUID.randomUUID().toLineString()
        val email = "$name@naver.com"
        val request = StudentDto.CreateRequest(
            name = name,
            gender = if(Random.nextBoolean()) Gender.MALE else Gender.FEMALE,
            email = email,
            age = Random.nextInt(99)
        )
        val student = studentRepository.save(Student.from(request))
        logger.info { "===================================" }
        logger.info { "student : ${student.toJsonString()}" }
        return student
    }

    /**
     * 템플릿 이용
     */
    private fun findOneByEmailUseQuery(email: String) {
        val query = Query()
        query.addCriteria(Criteria.where("email").`is`(email))

        val students = mongoTemplate.find(query, Student::class.java)
        if (students.isNotEmpty()) {
            DataInitializer.logger.info("find all by email[$email]")
            students.forEach { student ->
                DataInitializer.logger.info ("find one : ${student.toJsonString()}")
            }
        }
    }

    /**
     * 레파지토리 이용
     */
    private fun findAllByGenderUseNamedQuery(gender: Gender = Gender.MALE) {
        val students = studentRepository.findStudentsByGender(gender)
        if (students.isNotEmpty()) {
            DataInitializer.logger.info("find all by gender[$gender]")
            students.forEach { student ->
                DataInitializer.logger.info ("find one : ${student.toJsonString()}")
            }
        }
    }
}