package com.example.springbootconcurrencybasis.client.concert

import com.example.springbootconcurrencybasis.client.concert.resource.ConcertResource
import retrofit2.Call

interface ConcertClient {
    fun createConcert(resource: ConcertResource.CreateRequest): Call<ConcertResource.Reply>
}