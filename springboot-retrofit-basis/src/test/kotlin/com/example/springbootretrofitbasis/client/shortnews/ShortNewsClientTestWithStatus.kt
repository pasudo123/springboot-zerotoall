package com.example.springbootretrofitbasis.client.shortnews

import com.example.springbootretrofitbasis.MockWebServerSupport
import com.example.springbootretrofitbasis.MockWebServerSupport.RetrofitTestApi.buildRetrofitClient
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import okhttp3.mockwebserver.MockResponse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.nio.charset.Charset

@DisplayName("ShortNewsClient 는")
internal class ShortNewsClientTestWithStatus : MockWebServerSupport() {

    @Test
    @DisplayName("200 결과를 테스트한다.")
    fun result200Test() {

        // given
        val mockClient = buildRetrofitClient(baseUrl, ShortNewsClient::class.java)

        // when
        val response = mockClient.result200()
        val basic = response.execute().body()!!

        // then
        basic shouldNotBe null
        basic.message shouldBe "Hi Hello"
    }

    @Test
    @DisplayName("200 결과에 body empty 를 테스트한다.")
    fun result200BodyEmptyTest() {

        // given
        val mockClient = buildRetrofitClient(baseUrl, ShortNewsClient::class.java)

        // when
        val response = mockClient.result200BodyEmpty()
        val basic = response.execute().body()!!

        // then : 해당 값은 NullOrEmptyConverterFactory 에서 반환해준다.
        basic.message shouldBe "body is empty"
    }

    @Test
    @DisplayName("204 결과를 테스트한다.")
    fun result204Test() {

        // given
        val mockClient = buildRetrofitClient(baseUrl, ShortNewsClient::class.java)

        // when
        val response = mockClient.result204()
        val basic = response.execute().body()

        // then
        basic shouldBe null
    }

    @Test
    @DisplayName("404 결과를 테스트한다.")
    fun result404Test() {

        // given
        val mockClient = buildRetrofitClient(baseUrl, ShortNewsClient::class.java)

        // when
        val response = mockClient.result404().execute()

        // then
        response.isSuccessful shouldBe false
        response.code() shouldBe HttpStatus.NOT_FOUND.value()
        response.errorBody()!!.asClue {
            it.source().buffer.request(Long.MAX_VALUE)
            it.source().buffer.clone().readString(Charset.forName("UTF-8")) shouldBe "{ \"message\": \"404 NOT FOUND\" }"
        }
    }

    @Test
    @DisplayName("500 결과를 테스트한다.")
    fun result500Test() {

        // given
        val mockClient = buildRetrofitClient(baseUrl, ShortNewsClient::class.java)

        // when
        val response = mockClient.result500().execute()

        // then
        response.isSuccessful shouldBe false
        response.code() shouldBe HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.errorBody()!!.asClue {
            it.source().buffer.request(Long.MAX_VALUE)
            it.source().buffer.clone().readString(Charset.forName("UTF-8")) shouldBe "{ \"message\": \"INTERNAL SERVER ERROR\" }"
        }
    }

    @Test
    @DisplayName("500 결과지만, 별도의 mockWebServer enqueue() 를 추가하여 별도의 body 로 작성할 수 있도록 한다.")
    fun result500TestOnBodyAnother() {

        // given
        val mockClient = buildRetrofitClient(baseUrl, ShortNewsClient::class.java)
        server.enqueue(
            MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody("{ \"message\": \"force enqueue()\" }")
        )

        // when
        val response = mockClient.result500().execute().body()!!

        // then
        response.message shouldBe "force enqueue()"
    }

    @Test
    @DisplayName("resultXXX 를 테스트한다. : 별도의 MockResponse() 를 만들어본다.")
    fun resultXXXTest() {

        // given
        val mockClient = buildRetrofitClient(baseUrl, ShortNewsClient::class.java)
        server.enqueue(
            MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody("{ \"message\": \"sample ok\" }")
        )

        // when
        val response = mockClient.resultXXX().execute().body()!!

        // then
        response.message shouldBe "sample ok"
    }
}
