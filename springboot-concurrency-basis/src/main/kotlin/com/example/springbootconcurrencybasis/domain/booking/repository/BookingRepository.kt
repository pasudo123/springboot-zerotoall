package com.example.springbootconcurrencybasis.domain.booking.repository

import com.example.springbootconcurrencybasis.domain.booking.model.Booking
import org.springframework.data.jpa.repository.JpaRepository

interface BookingRepository : JpaRepository<Booking, Long>