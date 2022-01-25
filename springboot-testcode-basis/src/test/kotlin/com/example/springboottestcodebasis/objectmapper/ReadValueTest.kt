package com.example.springboottestcodebasis.objectmapper

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * https://kotlinlang.org/docs/object-declarations.html
 *
 * - object 키워드를 파라미터 내에 선언함으로써, 익명클래스 효과를 누릴 수 있음
 * - 일회용으로 유용함
 * - 기존 클래스를 상속하거나 인터페이스 구현을 할 수 있음
 * - 익명 클래스의 인스턴스는 이름이 아닌 표현식으로 정의되기 때문에 익명 오브젝트라고 불리기도 함
 */
class ReadValueTest {

    private val mapper = ObjectMapper().registerKotlinModule()

    @Test
    @DisplayName("json 을 리스트로 반환한다.")
    fun readValueToListTest() {

        // given

        // when
        val coffeeDtos = mapper.readValue(json, object: TypeReference<List<CoffeeDto>>() {})

        // then
        coffeeDtos.size shouldBe 3
        coffeeDtos[0].name shouldBe "coffee1"
        coffeeDtos[1].name shouldBe "coffee2"
        coffeeDtos[2].name shouldBe "coffee3"
    }
}

data class CoffeeDto(
    val name: String
)

val json = """
    [
        {
            "name": "coffee1"
        },
        {
            "name": "coffee2"
        },
        {
            "name": "coffee3"
        }
    ]
""".trimIndent()