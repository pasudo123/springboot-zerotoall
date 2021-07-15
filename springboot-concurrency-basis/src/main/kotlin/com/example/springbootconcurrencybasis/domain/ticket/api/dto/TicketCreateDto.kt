package com.example.springbootconcurrencybasis.domain.ticket.api.dto

import com.example.springbootconcurrencybasis.domain.ticket.model.Ticket

data class TicketCreateDto(
    val name: String,
    val initCount: Long,
    val price: Long,
    val seatGrade: Ticket.SeatGrade
)