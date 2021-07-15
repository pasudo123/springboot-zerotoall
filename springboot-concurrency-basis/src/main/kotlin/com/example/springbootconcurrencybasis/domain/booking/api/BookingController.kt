package com.example.springbootconcurrencybasis.domain.booking.api

import com.example.springbootconcurrencybasis.domain.booking.api.dto.BookingCreateDto
import com.example.springbootconcurrencybasis.domain.booking.model.Booking
import com.example.springbootconcurrencybasis.domain.booking.service.BookingService
import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("bookings")
class BookingController(
    private val bookingService: BookingService
) {

    companion object : KLogging()

    @PostMapping
    fun create(
        @RequestBody bookingCreateDto: BookingCreateDto
    ): ResponseEntity<Booking> {
        return ResponseEntity.ok(bookingService.create(bookingCreateDto))
    }

    @DeleteMapping("{id}")
    fun cancel(
        @PathVariable id: Long
    ): ResponseEntity<Unit> {
        bookingService.cancel(id)
        return ResponseEntity.ok(Unit)
    }
}