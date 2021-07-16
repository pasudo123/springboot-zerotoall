package com.example.springbootconcurrencybasis.domain.booking.service

import com.example.springbootconcurrencybasis.domain.booking.model.Booking
import com.example.springbootconcurrencybasis.domain.booking.repository.BookingRedisRepository
import com.example.springbootconcurrencybasis.domain.ticket.model.Ticket
import com.example.springbootconcurrencybasis.global.exception.ErrorCode
import com.example.springbootconcurrencybasis.global.exception.detail.SystemPolicyException
import mu.KLogging
import org.springframework.stereotype.Service

/**
 * redisTemplate 을 이용, incr 과 desc 를 이용하여 처리한다.
 */
@Service
class BookingChecker(
    private val bookingRedisRepository: BookingRedisRepository
) {

    companion object : KLogging()

    /**
     * bookingCount 는 lock 이 안걸려서, 동일한 값이 나오더라도
     * incrValue 는 레디스 싱글스레드로 동작하기에 동시성 체크가 가능하다.
     */
    fun possibleBookingOrThrow(booking: Booking, bookingCount: Int, ticket: Ticket) {
        val incrValue = bookingRedisRepository.increaseBooking(booking, bookingCount)
        if (incrValue > ticket.initCount) {
            this.decreaseBy(booking)
            throw SystemPolicyException(ErrorCode.SP100, ErrorCode.SP100.message)
        }
    }

    fun decreaseBy(booking: Booking) {
        bookingRedisRepository.decreaseBooking(booking)
    }
}