package com.example.springbootmongobasis.domain.banner.repository

import com.example.springbootmongobasis.domain.banner.model.Banner
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BannerRepository : MongoRepository<Banner, String> {

    fun findByName(name: String): Optional<Banner>

    fun findByHashName(hashName: String): Optional<Banner>
}