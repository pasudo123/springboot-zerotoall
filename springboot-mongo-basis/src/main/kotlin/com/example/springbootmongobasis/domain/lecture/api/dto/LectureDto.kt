package com.example.springbootmongobasis.domain.lecture.api.dto

class LectureDto {

    data class CreateRequest(
        val name: String
    )

    data class UpdateRequest(
        val score: Int
    )
}
