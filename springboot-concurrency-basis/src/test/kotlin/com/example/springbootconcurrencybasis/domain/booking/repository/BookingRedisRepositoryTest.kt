package com.example.springbootconcurrencybasis.domain.booking.repository

import com.example.springbootconcurrencybasis.Helper
import com.example.springbootconcurrencybasis.IntegrationSupport
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@DisplayName("BookingRedisRepository 는")
internal class BookingRedisRepositoryTest : IntegrationSupport() {

    @Autowired
    private lateinit var helper: Helper

    @Autowired
    private lateinit var bookingRedisRepository: BookingRedisRepository

    companion object : KLogging()

    @Test
    @DisplayName("레디스 키를 감시한다.")
    fun watchBookingTest() {

        // given
        val concert = helper.createConcert()
        val ticket = helper.createTicket(concertId = concert.id!!)
        val booking = helper.createBooking(concertId = concert.id!!, ticketId = ticket.id!!)

        // when
        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).launch {
                val result01 = async { bookingRedisRepository.watchBookingForTest(booking) }
                val result02 = async { bookingRedisRepository.watchBookingForTest(booking) }
                val result03 = async { bookingRedisRepository.watchBookingForTest(booking) }
                val result04 = async { bookingRedisRepository.watchBookingForTest(booking) }
                println("[1] ${result01.await()}")
                println("[2] ${result02.await()}")
                println("[3] ${result03.await()}")
                println("[4] ${result04.await()}")
            }
            job.join()
        }

        // then
        val result = bookingRedisRepository.get(booking)
        result shouldBe 1
    }
}