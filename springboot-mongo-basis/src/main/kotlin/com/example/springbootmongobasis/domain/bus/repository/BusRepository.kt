package com.example.springbootmongobasis.domain.bus.repository

import com.example.springbootmongobasis.domain.bus.model.Bus
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BusRepository : MongoRepository<Bus, String> {

    fun findByLocationNear(location: Point, distance: Distance): List<Bus>
}