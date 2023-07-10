package com.example.springbootbasis.api.streaming

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.time.LocalDateTime
import java.util.UUID
import java.util.concurrent.Executors

@RestController
@RequestMapping("sample-stream")
class SampleStreamingController {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("sse")
    fun useSse(): SseEmitter {
        val emitter = SseEmitter()
        val executor = Executors.newCachedThreadPool()
        executor.execute {
            try {
                (1..Long.MAX_VALUE).forEach {
                    val event = SseEmitter.event()
                        .data("mvc sse : number=$it(${LocalDateTime.now()})")
                        .id(UUID.randomUUID().toString())
                        .name("sse event")
                    emitter.send(event)
                    Thread.sleep(2000)
                }
            } catch (exception: Exception) {
                log.error("exception : ${exception.message}")
                emitter.completeWithError(exception)
            }
        }

        return emitter
    }
}