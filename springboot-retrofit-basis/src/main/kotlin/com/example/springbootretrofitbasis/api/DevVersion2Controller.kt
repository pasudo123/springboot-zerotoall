package com.example.springbootretrofitbasis.api

import com.example.springbootretrofitbasis.client.shortnews.ShortNewsClient
import com.example.springbootretrofitbasis.client.shortnews.model.ShortNewsResponse
import kotlinx.coroutines.delay
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import retrofit2.await

/**
 * 각각 로직이 있다는 가정아래, 500milli sec 텀을 주고 jmeter 로 동시 실행
 * 타임아웃이 나는 빈도는 일반 sync 메소드가 높다.
 */
@RestController
@RequestMapping("dev/v2")
class DevVersion2Controller(
    private val shortNewsClient: ShortNewsClient
) {

    @GetMapping("sync")
    fun getSync(): ResponseEntity<ShortNewsResponse> {
        val response = shortNewsClient
            .getNewsByCategory(ShortNewsClient.Category.random())
            .execute()
            .body()

        Thread.sleep(500)

        return ResponseEntity.ok(response)
    }

    @GetMapping("async-with-coroutine")
    suspend fun getAsyncWithCoroutine(): ResponseEntity<ShortNewsResponse> {
        val responseEntity: ResponseEntity<ShortNewsResponse>

        // controller 에서 client 로 건네주는 함수에서 suspend 가 붙을 시, runBlocking 으로 한번 감싸져있다.
        val result = shortNewsClient.getNewsByCategory(ShortNewsClient.Category.random()).await()
        responseEntity = ResponseEntity.ok(result)

        delay(500)

        return responseEntity
    }
}