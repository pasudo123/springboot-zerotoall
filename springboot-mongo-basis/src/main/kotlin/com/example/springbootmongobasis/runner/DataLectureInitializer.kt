package com.example.springbootmongobasis.runner

import com.example.springbootmongobasis.domain.lecture.api.dto.LectureDto
import com.example.springbootmongobasis.domain.lecture.model.Lecture
import com.example.springbootmongobasis.domain.lecture.repository.LectureRepository
import com.example.springbootmongobasis.domain.student.repository.StudentRepository
import mu.KLogging
import org.springframework.stereotype.Component

@Component
class DataLectureInitializer(
    private val lectureRepository: LectureRepository,
    private val studentRepository: StudentRepository
) {

    companion object : KLogging()

    fun process() {
        val student = studentRepository.findAll().first()
        deleteAll()
        val lectures = insert()

        lectures.forEach { lecture ->
            student.addLecture(lecture)
        }

        studentRepository.save(student)
    }

    private fun deleteAll() {
        lectureRepository.deleteAll()
    }

    private fun insert(): List<Lecture> {

        val lectures: MutableList<Lecture> = mutableListOf()
        val lectureNames = listOf(
            "geometry", "economic mathematics",
            "english", "korean", "world history",
            "physics", "chemistry"
        )

        for (lectureName in lectureNames) {
            val request = LectureDto.CreateRequest(
                name = lectureName
            )
            lectures.add(lectureRepository.save(Lecture.from(request)))
        }

        return lectures.toList()
    }
}