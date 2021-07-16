package com.example.springbootconcurrencybasis.domain.booking.api

import com.example.springbootconcurrencybasis.IntegrationSupport
import com.example.springbootconcurrencybasis.client.booking.BookingClient
import com.example.springbootconcurrencybasis.client.booking.BookingClientMaker
import com.example.springbootconcurrencybasis.client.booking.resource.BookingResource
import com.example.springbootconcurrencybasis.client.concert.ConcertClient
import com.example.springbootconcurrencybasis.client.concert.ConcertClientMaker
import com.example.springbootconcurrencybasis.client.concert.resource.ConcertResource
import com.example.springbootconcurrencybasis.client.ticket.TicketClient
import com.example.springbootconcurrencybasis.client.ticket.TicketClientMaker
import com.example.springbootconcurrencybasis.client.ticket.resource.TicketResource
import com.example.springbootconcurrencybasis.domain.concert.model.Concert
import com.example.springbootconcurrencybasis.domain.ticket.model.Ticket
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment

@DisplayName("BookingControllerConcurrency 는")
class BookingControllerConcurrencyTest : IntegrationSupport() {

    @Autowired
    private lateinit var env: Environment
    @Autowired
    private lateinit var mapper: ObjectMapper

    private lateinit var concertMaker: ConcertClientMaker
    private lateinit var concertClient: ConcertClient
    private lateinit var ticketMaker: TicketClientMaker
    private lateinit var ticketClient: TicketClient
    private lateinit var bookingMaker: BookingClientMaker
    private lateinit var bookingClient: BookingClient

    @BeforeEach
    fun init() {
        val host = env.getProperty("client.concert.host").toString()
        val useDummy = env.getProperty("client.concert.use-dummy").toBoolean()
        this.concertMaker = ConcertClientMaker(host, useDummy, mapper)
        this.concertClient = concertMaker.createClient()
        this.ticketMaker = TicketClientMaker(host, useDummy, mapper)
        this.ticketClient = ticketMaker.createClient()
        this.bookingMaker = BookingClientMaker(host, useDummy, mapper)
        this.bookingClient = bookingMaker.createClient()
    }

    @Test
    @DisplayName("콘서트를 하나를 생성한다.")
    @Disabled("로컬에서 앱을 띄어놓고 해야한다.")
    fun createConcertTest() {

        // given
        val resource = ConcertResource.CreateRequest(
            name = "아이유 콘서트 여름 2021",
            performanceDate = "2021-08-01 18:00:00",
            viewingTime = 120,
            grade = Concert.Grade.PG_12.name
        )

        // when
        val concert = concertClient.createConcert(resource)
            .execute()
            .body()

        // then
        concert shouldNotBe null
        concert!!.name shouldBe "아이유 콘서트 여름 2021"
        concert.viewingTime shouldBe 120
        concert.grade shouldBe Concert.Grade.PG_12.name
    }

    @Test
    @DisplayName("에약을 동시에 수행한다.")
    @Disabled("로컬에서 앱을 띄어놓고 해야한다.")
    fun createBookingConcurrencyTest() {

        // given : concert
        val concertRequest = ConcertResource.CreateRequest(
            name = "빅뱅 콘서트 겨울 VIM",
            performanceDate = "2021-12-01 18:00:00",
            viewingTime = 180,
            grade = Concert.Grade.PG_15.name
        )
        val concertReply = concertClient.createConcert(concertRequest)
            .execute()
            .body()!!

        concertReply shouldNotBe null

        // given : ticket
        val ticketRequest = TicketResource.TicketRequest(
            name = "빅뱅 콘서트 S 좌석",
            initCount = 5,
            price = 100000L,
            seatGrade = Ticket.SeatGrade.S_CLASS.name
        )
        val ticketReply = ticketClient.createTicket(concertReply.id, ticketRequest)
            .execute()
            .body()!!

        ticketReply shouldNotBe null

        // given : booking
        val bookingRequest = BookingResource.CreateRequest(
            concertId = concertReply.id,
            ticketId = ticketReply.id
        )
        var bookingReply = bookingCreateBy(bookingRequest)
        bookingReply shouldNotBe null

        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).launch {
                async { bookingCreateBy(bookingRequest) }
                async { bookingCreateBy(bookingRequest) }
                async { bookingCreateBy(bookingRequest) }
                async { bookingCreateBy(bookingRequest) }
            }
            job.join()
        }
    }

    private fun bookingCreateBy(request: BookingResource.CreateRequest): BookingResource.Reply? {
        return bookingClient.createBooking(request)
            .execute()
            .body()
    }
}