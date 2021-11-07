package com.example

import org.springframework.data.auditing.DateTimeProvider
import java.time.LocalDateTime
import java.time.temporal.TemporalAccessor
import java.util.*

class FakeDateTimeProvider(
    private val date: LocalDateTime
) : DateTimeProvider {

    override fun getNow(): Optional<TemporalAccessor> {
        return Optional.ofNullable(date)
    }
}