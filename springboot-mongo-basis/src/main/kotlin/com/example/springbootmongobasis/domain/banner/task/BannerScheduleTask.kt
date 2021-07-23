package com.example.springbootmongobasis.domain.banner.task

import com.example.springbootmongobasis.domain.banner.repository.BannerRepository
import mu.KLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(
    name = ["app.schedule.banner.enabled"],
    havingValue = "true",
    matchIfMissing = true
)
class BannerScheduleTask(
    private val bannerRepository: BannerRepository
) {

    companion object : KLogging()

    @Scheduled(initialDelay = 3000, fixedDelay = 5000)
    fun doTask() {
        val banners = bannerRepository.findAll()
        logger.info { "current banner count : ${banners.count()}" }
    }
}