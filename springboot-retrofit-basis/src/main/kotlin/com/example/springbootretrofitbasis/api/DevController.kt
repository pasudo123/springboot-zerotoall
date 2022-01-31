package com.example.springbootretrofitbasis.api

import com.example.springbootretrofitbasis.client.shortnews.ShortNewsClient
import com.example.springbootretrofitbasis.client.shortnews.model.ShortNewsResponse
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import retrofit2.awaitResponse

@RestController
@RequestMapping("dev")
class DevController(
    private val shortNewsClient: ShortNewsClient
) {

    @GetMapping("sync")
    fun getSync(): ResponseEntity<ShortNewsResponse> {
        val response = shortNewsClient
            .getNewsByCategory(ShortNewsClient.Category.random())
            .execute()
            .body()!!

        return ResponseEntity.ok(response)
    }

    @GetMapping("async-with-coroutine")
    fun getAsyncWithCoroutine(): ResponseEntity<ShortNewsResponse> {
        val responseEntity: ResponseEntity<ShortNewsResponse>

        runBlocking {
            val response = shortNewsClient.getNewsByCategory(ShortNewsClient.Category.random()).awaitResponse().body()!!
            responseEntity = ResponseEntity.ok(response)
        }

        return responseEntity
    }
}