package com.example.springbootretrofitbasis.api

import com.example.springbootretrofitbasis.client.shortnews.ShortNewsClient
import com.example.springbootretrofitbasis.client.shortnews.model.ShortNewsResponse
import mu.KLoggable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import retrofit2.await
import retrofit2.awaitResponse

@RestController
@RequestMapping("dev")
class DevController(
    private val shortNewsClient: ShortNewsClient
) {

    companion object : Any(), KLoggable {
        override val logger = logger()
    }

    @GetMapping("sync")
    fun getSync(): ResponseEntity<ShortNewsResponse> {
        val response = shortNewsClient
            .getNewsByCategory(ShortNewsClient.Category.random())
            .execute()
            .body()!!

        return ResponseEntity.ok(response)
    }

    @GetMapping("async-with-coroutine")
    suspend fun getAsyncWithCoroutine(): ResponseEntity<ShortNewsResponse> {
        val responseEntity: ResponseEntity<ShortNewsResponse>

        // controller 에서 client 로 건네주는 함수에서 suspend 가 붙을 시, runBlocking 으로 한번 감싸져있다.
        val result = shortNewsClient.getNewsByCategory(ShortNewsClient.Category.random()).await()
        responseEntity = ResponseEntity.ok(result)

        return responseEntity
    }

    @GetMapping("async-with-coroutine-error-handling")
    suspend fun getAsyncWithCoroutineErrorHandling(): ResponseEntity<ShortNewsResponse> {
        val responseEntity: ResponseEntity<ShortNewsResponse>

        val errorParam = "error"
        val response = shortNewsClient.getNewsByCategory(errorParam).awaitResponse()

        if (response.isSuccessful) {
            responseEntity = ResponseEntity.ok(response.body())
            return responseEntity
        }

        return ResponseEntity.internalServerError().build()
    }
}