package com.example.springbootmongobasis.domain.lecture.model

import com.example.springbootmongobasis.domain.BaseDocument
import com.example.springbootmongobasis.domain.lecture.api.dto.LectureDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "lecture")
class Lecture private constructor(
    val name: String
): BaseDocument() {

    @Id
    var id: String? = null
        protected  set

    var score: Int? = null
        protected  set

    fun changeScore(score: Int) {
        this.score = score
    }

    companion object {
        fun from(request: LectureDto.CreateRequest): Lecture {
            return request.run {
                Lecture(
                    this.name
                )
            }
        }
    }
}