package com.example.springbootquartzbasis.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

object TimeUtils

val SEOUL_ZONE_ID: ZoneId = ZoneId.of("Asia/Seoul")

fun Date.convertLocalDateTime(): LocalDateTime {
    return this.toInstant().atZone(SEOUL_ZONE_ID).toLocalDateTime()
}

fun LocalDateTime.convertDate(): Date {
    return Date.from(this.atZone(SEOUL_ZONE_ID).toInstant())
}

fun nowDate(): Date {
    return Date.from(ZonedDateTime.now(SEOUL_ZONE_ID).toInstant())
}

fun nowZone(): ZonedDateTime {
    return ZonedDateTime.now(SEOUL_ZONE_ID)
}

fun nowLocalDateTime(): LocalDateTime {
    return LocalDateTime.now(SEOUL_ZONE_ID)
}

fun asiaSeoulTimeZone(): TimeZone {
    return TimeZone.getTimeZone(SEOUL_ZONE_ID)
}

fun LocalDateTime.toZoneDateTime(): ZonedDateTime {
    return this.atZone(SEOUL_ZONE_ID)
}

fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this)
}