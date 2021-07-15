package com.example.springbootconcurrencybasis.client.ticket

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class TicketClientMaker(
    private val host: String,
    private val useDummy: Boolean,
    private val mapper: ObjectMapper
) {

    fun createClient(): TicketClient {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                    .header("Accept", "application/json")
                val request = builder.build()
                chain.proceed(request)
            }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .callFactory(httpClient)
            .build()

        if (useDummy) {
            return TicketLocalClient()
        }

        return retrofit.create(TicketRealClient::class.java)
    }
}