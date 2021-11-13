package com.example.springboottestcodebasis.domain.member.model

import com.example.setPrivateProperty
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicLong

object MockMember {

    private val atomicId = AtomicLong(1)

    fun create(
        name: String,
        age: Int,
        createdAt: LocalDateTime = LocalDateTime.now(),
        modifiedAt: LocalDateTime = LocalDateTime.now(),
        id: Long? = null
    ): Member {
        return Member(name, age).apply {
            this.setPrivateProperty("id", id ?: atomicId.getAndIncrement())
            this.setPrivateProperty("createdAt", createdAt)
            this.setPrivateProperty("modifiedAt", modifiedAt)
        }
    }
}