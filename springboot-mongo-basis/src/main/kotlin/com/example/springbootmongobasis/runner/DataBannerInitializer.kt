package com.example.springbootmongobasis.runner

import com.example.springbootmongobasis.domain.banner.api.dto.BannerDto
import com.example.springbootmongobasis.domain.banner.model.Banner
import com.example.springbootmongobasis.domain.banner.repository.BannerRepository
import com.example.springbootmongobasis.util.toJsonString
import com.example.springbootmongobasis.util.toLineString
import mu.KLogging
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataBannerInitializer(
    private val bannerRepository: BannerRepository,
    private val mongoTemplate: MongoTemplate
) {

    companion object : KLogging()

    fun process() {
        val banners = insert()
        for((currentIndex, banner) in banners.withIndex()){
            logger.info { "banner[$currentIndex] : ${banner.toJsonString()}" }
        }
    }

    private fun insert(): List<Banner> {

        val banners: MutableList<Banner> = mutableListOf()

        repeat(50) { sequence ->
            val name = UUID.randomUUID().toLineString()
            val request = BannerDto.CreateRequest(
                name = "$name 광고",
                desc = "$sequence description"
            )
            banners.add(bannerRepository.save(Banner.from(request)))
        }

        return banners.toList()
    }
}