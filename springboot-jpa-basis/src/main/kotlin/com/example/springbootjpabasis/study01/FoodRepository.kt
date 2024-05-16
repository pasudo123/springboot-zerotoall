package com.example.springbootjpabasis.study01

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FoodRepository : JpaRepository<Food, Long>
