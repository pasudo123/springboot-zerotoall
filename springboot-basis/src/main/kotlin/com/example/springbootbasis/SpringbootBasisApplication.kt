package com.example.springbootbasis

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * - defaultLockAtMostFor : 락이 죽어버린 경우 어느정도까지 락을 유지할 것인지 결정. PT0S, 죽으면 락을 0초만 유지
 */
@SpringBootApplication
@EnableSchedulerLock(defaultLockAtMostFor = "PT0S")
class SpringbootBasisApplication

fun main(args: Array<String>) {
    runApplication<SpringbootBasisApplication>(*args)
}
