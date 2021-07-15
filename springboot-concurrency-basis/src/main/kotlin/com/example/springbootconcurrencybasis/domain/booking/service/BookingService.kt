package com.example.springbootconcurrencybasis.domain.booking.service

import com.example.springbootconcurrencybasis.domain.booking.api.dto.BookingCreateDto
import com.example.springbootconcurrencybasis.domain.booking.model.Booking
import com.example.springbootconcurrencybasis.domain.booking.repository.BookingCustomRepository
import com.example.springbootconcurrencybasis.domain.booking.repository.BookingRepository
import com.example.springbootconcurrencybasis.domain.concert.service.ConcertFindService
import com.example.springbootconcurrencybasis.global.exception.ErrorCode
import com.example.springbootconcurrencybasis.global.exception.detail.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BookingService(
    private val bookingChecker: BookingChecker,
    private val bookingRepository: BookingRepository,
    private val bookingCustomRepository: BookingCustomRepository,
    private val concertFinderService: ConcertFindService,
) {

    fun create(bookingCreateDto: BookingCreateDto): Booking {
        val bookings = bookingCustomRepository.findAllBy(
            concertId = bookingCreateDto.concertId,
            ticketId = bookingCreateDto.ticketId
        )

        val concert = concertFinderService.findOneByIdOrThrow(bookingCreateDto.concertId)
        val ticket = concert.tickets.find { it.id == bookingCreateDto.ticketId }!!

        val booking = Booking().apply{
            this.set(concert)
            this.set(ticket)
        }

        bookingRepository.save(booking)
        bookingChecker.possibleBookingOrThrow(booking, bookings.count(), ticket)
        return booking
    }

    fun cancel(id: Long) {
        val booking = bookingRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException(ErrorCode.E100, "Booking[$id] 를 찾을 수 없습니다.")
        booking.delete()
        bookingRepository.save(booking)
        bookingChecker.decreaseBy(booking)
    }
}