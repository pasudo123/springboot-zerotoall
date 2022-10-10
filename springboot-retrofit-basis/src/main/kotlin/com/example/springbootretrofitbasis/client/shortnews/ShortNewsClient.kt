package com.example.springbootretrofitbasis.client.shortnews

import com.example.springbootretrofitbasis.client.shortnews.model.SampleResponse
import com.example.springbootretrofitbasis.client.shortnews.model.ShortNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShortNewsClient {

    enum class Category(val param: String) {
        ALL("all"),
        BUSINESS("business"),
        SPORTS("sports"),
        WORLD("world"),
        TECHNOLOGY("technology"),
        SCIENCE("science");

        companion object {
            fun random(): String {
                return values().toList().shuffled().first().param
            }
        }
    }

    @GET("news")
    fun getNewsByCategory(@Query("type") category: String): Call<ShortNewsResponse>

    @GET("news")
    suspend fun getNewsByCategoryWithCoroutine(@Query("type") category: String): ShortNewsResponse

    @GET("result-200")
    fun result200(): Call<SampleResponse>

    @GET("result-200-body-empty")
    fun result200BodyEmpty(): Call<SampleResponse>

    @GET("result-204")
    fun result204(): Call<SampleResponse>

    @GET("result-404")
    fun result404(): Call<SampleResponse>

    @GET("result-500")
    fun result500(): Call<SampleResponse>

    @GET("result-xxx")
    fun resultXXX(): Call<SampleResponse>
}
