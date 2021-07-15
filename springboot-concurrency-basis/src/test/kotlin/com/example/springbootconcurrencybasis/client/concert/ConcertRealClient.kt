package com.example.springbootconcurrencybasis.client.concert

import com.example.springbootconcurrencybasis.client.concert.resource.ConcertResource
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ConcertRealClient : ConcertClient{
    @POST("concerts")
    override fun createConcert(
        @Body resource: ConcertResource.CreateRequest
    ): Call<ConcertResource.Reply>
}