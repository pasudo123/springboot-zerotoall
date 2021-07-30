package com.example.springbootmongobasis.domain.bus.task

import com.example.springbootmongobasis.domain.bus.repository.BusRepository
import mu.KLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metric
import org.springframework.data.geo.Metrics
import org.springframework.data.geo.Point
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(
    name = ["app.schedule.busmove.enabled"],
    havingValue = "true",
    matchIfMissing = true
)
class BusMoveScheduler(
    private val busRepository: BusRepository
) {

    companion object : KLogging()

    @Scheduled(initialDelay = 3000, fixedDelay = 6000)
    fun findPossibleCallBusesTask(lat: Double = 37.480974212020016, lng: Double = 126.95245650915042) {
        logger.info { "==================== findPossibleCallBusesTask ====================" }
        busRepository.findByLocationNear(Point(lng, lat), Distance(50.0, Metric.))
    }
}