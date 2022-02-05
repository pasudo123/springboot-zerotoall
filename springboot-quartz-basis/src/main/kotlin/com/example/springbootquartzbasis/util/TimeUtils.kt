package com.example.springbootquartzbasis.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object TimeUtils

val seoulZoneId: ZoneId = ZoneId.of("Asia/Seoul")

fun Date.convertLocalDateTime(): LocalDateTime {
    return this.toInstant().atZone(seoulZoneId).toLocalDateTime()
}

fun seoulTimeZone(): TimeZone = TimeZone.getTimeZone(seoulZoneId)
