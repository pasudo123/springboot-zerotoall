package com.example.springbootjpabasis.event

import com.example.springbootjpabasis.domain.post.repository.PostRepository
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime

@Component
class PostEventListener(
    private val postRepository: PostRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Async
    // @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @EventListener
    @Transactional
    fun asyncEvent(postEvent: PostEvent) {
        log.info("event-enter... now=${LocalTime.now()}")

        val post = postRepository.findByIdOrNull(postEvent.postId)!!
        post.update("Event-Hello")
    }
}
