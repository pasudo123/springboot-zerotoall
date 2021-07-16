package com.example.springbootconcurrencybasis.domain.booking.service

import com.example.springbootconcurrencybasis.domain.booking.model.Booking
import com.example.springbootconcurrencybasis.domain.booking.repository.BookingRedisRepository
import com.example.springbootconcurrencybasis.domain.ticket.model.Ticket
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class BookingWatcher(
    private val bookingRedisRepository: BookingRedisRepository
) {

    companion object : KLogging()

    fun possibleBookingOrThrow(booking: Booking, bookingCount: Int, ticket: Ticket) {
        
    }
}