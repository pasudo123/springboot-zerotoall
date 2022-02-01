package com.example.springbootretrofitbasis.client.shortnews

import com.example.springbootretrofitbasis.MockWebServerSupport
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("ShortNewsClient 는")
class ShortNewsClientTest : MockWebServerSupport() {

    @Test
    @DisplayName("카테고리별 뉴스를 들고온다. 200 코드가 떨어진다.")
    fun getNewsByCategoryTest() {

        // given
        val json = """
            {
            	"category": "all",
            	"data": [{
            		"author": "Sakshi Sharma",
            		"content": "Maharashtra government further relaxed COVID-19 restrictions, allowing all tourist spots to remain open as per regular timings for visitors who have been fully vaccinated. Additional relaxations were given to 11 districts, including Mumbai, where over 90% population has been vaccinated with the first dose and 70% is fully vaccinated. Beaches, garden and parks will remain open in these districts.",
            		"date": "01 Feb 2022,Tuesday",
            		"imageUrl": "https://static.inshorts.com/inshorts/images/v1/variants/jpg/m/2022/02_feb/1_tue/img_1643685105131_863.jpg?",
            		"readMoreUrl": "https://twitter.com/ANI/status/1488225660464398337?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts ",
            		"time": "09:18 am",
            		"title": "Maharashtra relaxes COVID curbs, parks & beaches to open in 11 districts",
            		"url": "https://www.inshorts.com/en/news/maharashtra-relaxes-covid-curbs-parks-beaches-to-open-in-11-districts-1643687308052"
            	}],
                "success": "true"
            }
        """.trimIndent()
        val mockClient = RetrofitTestApi.buildRetrofitClient(baseUrl, ShortNewsClient::class.java)
        server.enqueue(
            MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody(json)
        )

        // when
        val response = mockClient.getNewsByCategory(ShortNewsClient.Category.random()).execute().body()!!

        // then
        response.category shouldBe "all"
        response.data.size shouldBeGreaterThanOrEqual 1
        response.success shouldBe true
    }

    @Test
    @DisplayName("async 로 suspend 가 붙은 카테고리별 뉴스를 들고온다.")
    fun getNewsByCategoryWithCoroutineTest() {

        // given
        val json = """
            {
            	"category": "all",
            	"data": [{
            		"author": "Sakshi Sharma",
            		"content": "Maharashtra government further relaxed COVID-19 restrictions, allowing all tourist spots to remain open as per regular timings for visitors who have been fully vaccinated. Additional relaxations were given to 11 districts, including Mumbai, where over 90% population has been vaccinated with the first dose and 70% is fully vaccinated. Beaches, garden and parks will remain open in these districts.",
            		"date": "01 Feb 2022,Tuesday",
            		"imageUrl": "https://static.inshorts.com/inshorts/images/v1/variants/jpg/m/2022/02_feb/1_tue/img_1643685105131_863.jpg?",
            		"readMoreUrl": "https://twitter.com/ANI/status/1488225660464398337?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts ",
            		"time": "09:18 am",
            		"title": "Maharashtra relaxes COVID curbs, parks & beaches to open in 11 districts",
            		"url": "https://www.inshorts.com/en/news/maharashtra-relaxes-covid-curbs-parks-beaches-to-open-in-11-districts-1643687308052"
            	}],
                "success": "true"
            }
        """.trimIndent()
        val mockClient = RetrofitTestApi.buildRetrofitClient(baseUrl, ShortNewsClient::class.java)
        server.enqueue(
            MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody(json)
        )

        runBlocking {
            // when
            val response = mockClient.getNewsByCategoryWithCoroutine(ShortNewsClient.Category.random())

            // then
            response.category shouldBe "all"
            response.data.size shouldBeGreaterThanOrEqual 1
            response.success shouldBe true
        }
    }
}