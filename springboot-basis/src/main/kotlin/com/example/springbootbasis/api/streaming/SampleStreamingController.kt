package com.example.springbootbasis.api.streaming

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.UUID
import java.util.concurrent.Executors

@RestController
@RequestMapping("sample-stream")
class SampleStreamingController {

    private val executor = Executors.newCachedThreadPool()

    @GetMapping("sse")
    fun useSse(): SseEmitter {
        val emitter = RyeSseEmitter()
        emitter.sendEvent()
        return emitter
    }

    private fun SseEmitter.sendEvent() {
        executor.execute {
            try {
                (1..Long.MAX_VALUE).forEach {
                    val person = Person("홍길동", it)
                    val event = SseEmitter.event()
                        // .data("hello-$it")
                        .data(person, MediaType.APPLICATION_JSON)
                        .name(EmitterEvent.USER_NAME_EVENT.name)
                        .id(UUID.randomUUID().toString())
                    this.send(event)
                    Thread.sleep(2000)
                }
            } catch (exception: Exception) {
                this.completeWithError(exception)
            }
        }
    }

    data class Person(
        val name: String,
        val age: Long
    )

    enum class EmitterEvent {
        USER_NAME_EVENT
    }
}
