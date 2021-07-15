package com.example.springbootconcurrencybasis.domain.booking.api.dto

data class BookingCreateDto(
    val concertId: Long,
    val ticketId: Long
)