package com.example.springbootbasis.scheduler

import jakarta.annotation.PostConstruct
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime

/**
 * AOP 기반으로 동작하기 위해 해당 클래스에 open 키워드 필요.
 * https://github.com/lukas-krecan/ShedLock#modes-of-spring-integration
 */
open class MyScheduler {

    private val log = LoggerFactory.getLogger(javaClass)

    private var name: String? = null
        get() = field ?: "empty"

    @PostConstruct
    fun postConstruct() {
        log.info("postConstruct ... :: $name")
    }

    @Scheduled(initialDelay = 3000, fixedDelay = 3000, zone = "Asia/Seoul")
    @SchedulerLock(
        name = "test",
        lockAtMostFor = "PT3S", // 최소 실행노드가 죽었을 때, 락을 얼만큼 유지할 건지
        lockAtLeastFor = "PT2S" // 최소 얼만큼 락을 유지할 건지
    )
    open fun task() {
        log.info("task... :: $name :: ${LocalDateTime.now()}")
    }

    fun setName(name: String) {
        this.name = name
    }
}
