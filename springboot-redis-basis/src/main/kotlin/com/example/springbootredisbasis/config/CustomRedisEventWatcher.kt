package com.example.springbootredisbasis.config

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CustomRedisEventWatcher {

    private val log = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun handleRedisEvent(rEvent: CustomREvent) {
        val line = StringBuilder().apply {
            this.appendLine()
            this.appendLine("event-state=${rEvent.currentState()}")
            this.appendLine("event-redis-uri=${rEvent.getRedisUriOrEmpty()}")
            this.appendLine("event-remote-address=${rEvent.getRemoteAddressOrEmpty()}")
        }
        if (rEvent.currentState() == CustomREvent.RState.NONE) {
            log.error("event check!! : ${rEvent.getInfoOrNull()}")
            return
        }
        log.info(line.toString())
    }
}