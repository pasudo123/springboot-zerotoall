package com.example.springbootconcurrencybasis.domain.booking.api

import com.example.springbootconcurrencybasis.IntegrationSupport
import com.example.springbootconcurrencybasis.domain.booking.api.dto.BookingCreateDto
import com.example.springbootconcurrencybasis.domain.booking.model.Booking
import com.example.springbootconcurrencybasis.domain.booking.repository.BookingRedisRepository
import com.example.springbootconcurrencybasis.domain.concert.api.ConcertController
import com.example.springbootconcurrencybasis.domain.concert.api.dto.ConcertCreateDto
import com.example.springbootconcurrencybasis.domain.concert.model.Concert
import com.example.springbootconcurrencybasis.domain.ticket.api.TicketController
import com.example.springbootconcurrencybasis.domain.ticket.api.dto.TicketCreateDto
import com.example.springbootconcurrencybasis.domain.ticket.model.Ticket
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import mu.KLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

@DisplayName("BookingController 는")
internal class BookingControllerTest : IntegrationSupport() {

    @Autowired
    private lateinit var concertController: ConcertController

    @Autowired
    private lateinit var ticketController: TicketController

    @Autowired
    private lateinit var bookingController: BookingController

    @Autowired
    private lateinit var bookingRedisRepository: BookingRedisRepository

    companion object : KLogging()

    @Test
    @DisplayName("콘서트 티켓을 예매한다.")
    fun createBookingTest() {

        // given
        val concertCreateResource = ConcertCreateDto(
            name = "아이유 콘서트 여름 2021",
            performanceDate = LocalDateTime.now(),
            viewingTime = 120,
            grade = Concert.Grade.PG_12
        )
        val concert = concertController.create(concertCreateResource).body!!
        concert shouldNotBe null

        val ticketCreateResource = TicketCreateDto(
            name = "아이유 콘서트 여름 2021 S 좌석 스탠딩",
            initCount = 10,
            price = 50000L,
            seatGrade = Ticket.SeatGrade.S_CLASS
        )
        val ticket = ticketController.create(concert.id!!, ticketCreateResource).body!!
        ticket shouldNotBe null

        val bookingCreateResource = BookingCreateDto(
            concertId = concert.id!!,
            ticketId = ticket.id!!
        )

        // when
        bookingController.create(bookingCreateResource).body!!
        bookingController.create(bookingCreateResource).body!!
        bookingController.create(bookingCreateResource).body!!
        val booking = bookingController.create(bookingCreateResource).body!!

        // then
        booking shouldNotBe null
        val count = bookingRedisRepository.get(booking)
        count shouldBe 4
    }

    @Test
    @DisplayName("콘서트 티켓을 동시에 예매한다.")
    fun createBookingConcurrencyTest() {

        // given
        val concertCreateResource = ConcertCreateDto(
            name = "아이유 콘서트 여름 2021",
            performanceDate = LocalDateTime.now(),
            viewingTime = 120,
            grade = Concert.Grade.PG_12
        )
        val concert = concertController.create(concertCreateResource).body!!
        concert shouldNotBe null

        val ticketCreateResource = TicketCreateDto(
            name = "아이유 콘서트 여름 2021 S 좌석 스탠딩",
            initCount = 5,
            price = 50000L,
            seatGrade = Ticket.SeatGrade.S_CLASS
        )
        val ticket = ticketController.create(concert.id!!, ticketCreateResource).body!!
        ticket shouldNotBe null

        val bookingCreateResource = BookingCreateDto(
            concertId = concert.id!!,
            ticketId = ticket.id!!
        )

        // when
        val booking: Booking = bookingController.create(bookingCreateResource).body!!
        booking shouldNotBe null

        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).launch {
                async { bookingCreateBy(bookingCreateResource) }
                async { bookingCreateBy(bookingCreateResource) }
                async { bookingCreateBy(bookingCreateResource) }
                async { bookingCreateBy(bookingCreateResource) }
            }
            job.join()
        }

        // then
        val count = bookingRedisRepository.get(booking)
        count shouldBe 5
    }

    @Test
    @DisplayName("콘서트 티켓을 동시에 예매하고, 그 중 하나에서 에러가 발생한다.")
    fun createBookingExceptionOverFlowTest() {

        // given : concert
        val concertCreateResource = ConcertCreateDto(
            name = "아이유 콘서트 여름 2021",
            performanceDate = LocalDateTime.now(),
            viewingTime = 120,
            grade = Concert.Grade.PG_12
        )
        val concert = concertController.create(concertCreateResource).body!!
        concert shouldNotBe null

        // given : ticket
        val ticketCreateResource = TicketCreateDto(
            name = "아이유 콘서트 여름 2021 S 좌석 스탠딩",
            initCount = 5,
            price = 50000L,
            seatGrade = Ticket.SeatGrade.S_CLASS
        )
        val ticket = ticketController.create(concert.id!!, ticketCreateResource).body!!
        ticket shouldNotBe null

        val bookingCreateResource = BookingCreateDto(
            concertId = concert.id!!,
            ticketId = ticket.id!!
        )

        // when & then
        val booking: Booking = bookingController.create(bookingCreateResource).body!!

        // initCount 의 개수보다 많은 예약을 수행한다.
        runBlocking {
            supervisorScope {
                val job = CoroutineScope(Dispatchers.IO).launch {
                    repeat(5) {
                        val result = async { bookingCreateBy(bookingCreateResource) }

                        try {
                            result.await()
                        } catch (exception: Exception) {
                            logger.info { "=== [여기-result] ===" }
                            logger.info { "${exception.message}" }
                            exception.message shouldBe "티켓 예약을 더 이상 할 수 없습니다."
                        }
                    }
                }
                try {
                    job.join()
                } catch (exception: Exception) {
                    logger.info { "[2]=== [여기-result] ===" }
                    logger.info { "[2] ${exception.message}" }
                    exception.message shouldBe "티켓 예약을 더 이상 할 수 없습니다."
                }
            }
        }

        // then
        val count = bookingRedisRepository.get(booking)
        count shouldBe 5
    }

    private fun bookingCreateBy(resource: BookingCreateDto) {
        bookingController.create(resource).body!!
    }
}