package com.example.springbootmongobasis.poc01.domain

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CafeRepository : MongoRepository<Cafe, ObjectId> {
}