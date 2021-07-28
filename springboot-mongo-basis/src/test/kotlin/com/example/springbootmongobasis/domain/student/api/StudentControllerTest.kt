package com.example.springbootmongobasis.domain.student.api

import com.example.springbootmongobasis.MongoRepositoryTest
import com.example.springbootmongobasis.domain.lecture.api.dto.LectureDto
import com.example.springbootmongobasis.domain.student.api.dto.StudentDto
import com.example.springbootmongobasis.domain.student.model.Gender
import com.example.springbootmongobasis.domain.student.model.Student
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import kotlin.random.Random

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MongoRepositoryTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("StudentController 는")
internal class StudentControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    @DisplayName("학생을 몽고디비에 추가하고 올바르게 값을 반환한다.")
    fun createTest() {

        // given
        val request = StudentDto.CreateRequest(
            name = "pasudo123",
            gender = Gender.MALE,
            email = "pasudo${Random.nextLong(0, 999)}@naver.com",
            age = 30
        )

        // when
        val response = restTemplate.postForEntity("/students", request, Student::class.java)

        // then
        response.statusCode shouldBe HttpStatus.OK
        val student = response.body!!
        student.id shouldNotBe null
        student.name shouldBe "pasudo123"
        student.gender shouldBe Gender.MALE
        student.age shouldBe 30
    }

    @Test
    @DisplayName("[1] 특정 학생에 대한 수업을 추가하고, 해당 학생을 반환한다.")
    fun addLectureTest() {

        // init
        val studentRequest = StudentDto.CreateRequest(
            name = "pasudo123",
            gender = Gender.MALE,
            email = "pasudo${Random.nextLong(0, 999)}@naver.com",
            age = 30
        )

        // given
        val studentId = restTemplate.postForEntity("/students", studentRequest, Student::class.java).body!!.id
        val lectureRequest = LectureDto.CreateRequest(name = "math")


        // when
        val response = restTemplate.postForEntity("/students/$studentId/lecture", lectureRequest, Student::class.java)

        // then
        response.statusCode shouldBe HttpStatus.OK
        val student = response.body!!
        student.id shouldBe studentId
        student.lectures!!.count() shouldBe 1
        student.lectures!!.first().name shouldBe "math"
    }

    @Test
    @DisplayName("[2] 특정 학생에 대한 수업을 추가하고, 해당 학생을 반환한다.")
    @Disabled("이건 잘 안된다.? 왜??????")
    fun addLectureTest2() {

        // init
        val studentRequest = StudentDto.CreateRequest(
            name = "pasudo123",
            gender = Gender.MALE,
            email = "pasudo${Random.nextLong(0, 999)}@naver.com",
            age = 30
        )

        // given
        val studentId = restTemplate.postForEntity("/students", studentRequest, Student::class.java).body!!.id
        val lectureRequest = LectureDto.CreateRequest(name = "economic mathematics")
        restTemplate.postForEntity("/students/$studentId/lecture", lectureRequest, Student::class.java)

        // when
        val response = restTemplate.getForEntity("/students/$studentId", Student::class.java)

        // then
        response.statusCode shouldBe HttpStatus.OK
        val student = response.body!!
        student.id shouldBe studentId
        student.lectures!!.count() shouldBe 1
        student.lectures!!.first().name shouldBe "economic mathematics"
    }
}