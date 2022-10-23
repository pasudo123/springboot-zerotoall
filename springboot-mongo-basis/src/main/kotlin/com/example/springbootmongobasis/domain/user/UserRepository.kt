package com.example.springbootmongobasis.domain.user

import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface UserRepository : MongoRepository<User, ObjectId> {

    fun findAllByNameStartsWith(name: String, pageable: Pageable): Page<User>

    fun findAllByCreatedAtBefore(now: LocalDateTime, pageable: Pageable): Page<User>
}
