package com.example.springbootmongobasis

import com.example.springbootmongobasis.domain.student.api.dto.StudentDto
import com.example.springbootmongobasis.domain.student.model.Gender
import com.example.springbootmongobasis.domain.student.model.Student
import com.example.springbootmongobasis.domain.student.repository.StudentRepository
import com.example.springbootmongobasis.util.toJsonString
import mu.KLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.random.Random

@Component
class DataInitializer(
    private val studentRepository: StudentRepository,
    private val mongoTemplate: MongoTemplate
) : CommandLineRunner{

    companion object : KLogging()

    override fun run(vararg args: String?) {
        val name = UUID.randomUUID().toString().replace("-", "").substring(0, 10)
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
        findOneByEmailUseQuery(email)
        findAllByGenderUseNamedQuery()
    }

    private fun findOneByEmailUseQuery(email: String) {
        val query = Query()
        query.addCriteria(Criteria.where("email").`is`(email))

        val students = mongoTemplate.find(query, Student::class.java)
        if (students.isNotEmpty()) {
            logger.info("find all by email[$email]")
            students.forEach { student ->
                logger.info ("find one : ${student.toJsonString()}")
            }
        }
    }

    private fun findAllByGenderUseNamedQuery(gender: Gender = Gender.MALE) {
        val students = studentRepository.findStudentsByGender(gender)
        if (students.isNotEmpty()) {
            logger.info("find all by gender[$gender]")
            students.forEach { student ->
                logger.info ("find one : ${student.toJsonString()}")
            }
        }
    }
}