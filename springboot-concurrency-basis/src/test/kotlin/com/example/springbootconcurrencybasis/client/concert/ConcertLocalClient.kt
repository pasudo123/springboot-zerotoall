package com.example.springbootconcurrencybasis.client.concert

import com.example.springbootconcurrencybasis.client.concert.resource.ConcertResource
import retrofit2.Call

class ConcertLocalClient : ConcertClient {
    override fun createConcert(
        resource: ConcertResource.CreateRequest
    ): Call<ConcertResource.Reply> {
        TODO("")
    }
}