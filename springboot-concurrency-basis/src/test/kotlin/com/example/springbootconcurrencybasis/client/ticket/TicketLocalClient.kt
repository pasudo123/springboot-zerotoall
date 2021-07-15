package com.example.springbootconcurrencybasis.client.ticket

import com.example.springbootconcurrencybasis.client.ticket.resource.TicketResource
import retrofit2.Call

class TicketLocalClient : TicketClient {
    override fun createTicket(
        id: Long,
        resource: TicketResource.TicketRequest
    ): Call<TicketResource.Reply> {
        TODO("Not yet implemented")
    }
}