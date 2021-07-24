package com.example.springbootpageablebasis.api

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("PageableController 는")
internal class PageableControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate
    private val mapper = ObjectMapper()

    @Test
    @DisplayName("@RequestParam 으로 매핑된 데이터를 확인한다.")
    fun requestParamsTest() {

        // given
        val page = 1
        val size = 10
        val sortBy = "modifiedAt"
        val sortOrder = SortOrder.ASC

        // when
        val response = restTemplate.getForEntity<Pageable>("/search/request-params?page=$page&size=$size&sortBy=$sortBy&sortOrder=$sortOrder", Pageable::class.java)

        // then
        response.statusCode shouldBe HttpStatus.OK
        val pageable = response.body!!
        pageable.page shouldBe page
        pageable.size shouldBe size
        pageable.sortBy shouldBe sortBy
        pageable.sortOrder shouldBe sortOrder
    }

    @Test
    @DisplayName("Map 자료구조로 매핑된 pageable 을 확인한다.")
    fun queryMapTest() {

        // given
        val page = 1
        val size = 3
        val sortBy = "createdAt"
        val sortOrder = SortOrder.DESC

        // when
        val response = restTemplate.getForEntity<Pageable>("/search/query-map?page=$page&size=$size&sortBy=$sortBy&sortOrder=$sortOrder", Pageable::class.java)

        // then
        val pageable = response.body!!
        pageable.page shouldBe page
        pageable.size shouldBe size
        pageable.sortBy shouldBe sortBy
        pageable.sortOrder shouldBe sortOrder
    }

    @Test
    @DisplayName("Pojo 클래스로 매핑된 pageable 을 확인한다.")
    fun pojoTest() {

        // given
        val page = 5
        val size = 5
        val sortBy = "createdAt"
        val sortOrder = SortOrder.ASC

        // when
        val response = restTemplate.getForEntity<Pageable>("/search/pojo?page=$page&size=$size&sortBy=$sortBy&sortOrder=$sortOrder", Pageable::class.java)

        // then
        val pageable = response.body!!
        pageable.page shouldBe page
        pageable.size shouldBe size
        pageable.sortBy shouldBe sortBy
        pageable.sortOrder shouldBe sortOrder
    }
}