package com.example.springbootmongobasis.domain.student.api.dto

import com.example.springbootmongobasis.domain.student.model.Gender

class StudentDto {

    data class CreateRequest(
        val name: String,
        val gender: Gender,
        val email: String,
        val age: Int
    )
}