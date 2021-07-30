package com.example.springbootmongobasis.domain.bus.task

import com.example.springbootmongobasis.domain.bus.repository.BusRepository
import mu.KLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.geo.Distance
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

    /**
     * @scheduled 에는 인자를 받을 수 없다.
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 6000)
    fun findPossibleCallBusesTask() {

        // 서울대입구역의 위치
        val lat: Double = 37.480974212020016
        val lng: Double = 126.95245650915042
        logger.info { "==================== findPossibleCallBusesTask ====================" }
        val availableBuses = busRepository.findByLocationNear(Point(lng, lat), Distance(0.1, Metrics.KILOMETERS))
        logger.info("count : $${availableBuses.count()}")
    }
}