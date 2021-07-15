package com.example.springbootconcurrencybasis.client.concert

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class ConcertClientMaker(
    private val host: String,
    private val useDummy: Boolean,
    private val mapper: ObjectMapper
) {

    fun createClient(): ConcertClient {
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


        if (this.useDummy) {
            return ConcertLocalClient()
        }

        return retrofit.create(ConcertRealClient::class.java)
    }
}