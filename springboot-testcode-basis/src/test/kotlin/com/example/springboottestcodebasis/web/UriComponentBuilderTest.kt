package com.example.springboottestcodebasis.web

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.web.util.UriComponentsBuilder


@DisplayName("UriComponentBuilder 는")
class UriComponentBuilderTest {

    @Test
    @DisplayName("정규식 var 값을 확장한다.")
    fun expandWithRegexVar() {

        // given
        val template = "/myurl/{name:[a-z]{1,5}}/show"
        var uriComponents = UriComponentsBuilder
            .fromUriString(template)
            .build()

        // when
        uriComponents = uriComponents.expand(mapOf("name" to "test"))

        // then
        uriComponents.path shouldBe "/myurl/test/show"
    }

    @Test
    @DisplayName("query string 에 대한 값을 채워넣는다.")
    fun constructUriWithQueryParameter() {

        // given
        val uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http").host("www.google.com")
            .path("/").query("q={keyword}").buildAndExpand("baeldung")

        // when & then
        uriComponents.toUriString() shouldBe "http://www.google.com/?q=baeldung"
    }

    @Test
    @DisplayName("query path 에 대한 값을 채워넣는다.")
    fun constructUriFromTemplate() {

        // given
        val uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http").host("www.baeldung.com")
            .path("/{article-name}").buildAndExpand("junit-5")

        // when & then
        uriComponents.toUriString() shouldBe "http://www.baeldung.com/junit-5"
    }
}