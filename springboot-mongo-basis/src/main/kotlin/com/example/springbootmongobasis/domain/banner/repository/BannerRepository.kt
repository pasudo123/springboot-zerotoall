package com.example.springbootmongobasis.domain.banner.repository

import com.example.springbootmongobasis.domain.banner.model.Banner
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BannerRepository : MongoRepository<Banner, String> {
}