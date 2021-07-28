package com.example.springbootmongobasis.domain.lecture.repository

import com.example.springbootmongobasis.domain.lecture.model.Lecture
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LectureRepository : MongoRepository<Lecture, String> {
}