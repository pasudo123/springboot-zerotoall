package com.example.springbootjpabasis.jackson

import com.example.springbootjpabasis.config.toJson
import com.example.springbootjpabasis.config.toObject
import com.example.springbootjpabasis.config.toObjectList
import org.junit.jupiter.api.Test

class JacksonTest {

    @Test
    fun `databind test`() {
        // 직렬화 : single lowercase 에는 문제가 있음. @get:JsonProperty or @JvmField 적용이 필요.
        val dummy = Dummy(aName = "hello", bName = "hi", aaName = "sorry")
        println(dummy.toJson())

        // 역직렬화 : single lowercase 에는 문제가 없음
        val json = """
            {
                "aName": "hello-aName",
                "bName": "hello-bName",
                "aaName": "sorry-aaName"
            }
        """.trimIndent()

        println(json.toObject<Dummy>().toString())
    }

    @Test
    fun `coffeeDto 를 변환, deserialize 한다`() {


        val json = """
            [{"name":"아메리카노4"},{"name":"아메리카노5"},{"name":"아메리카노6"}]
        """.trimIndent()

        val coffeeDtos = json.toObjectList<CoffeeDto>()
        println(coffeeDtos)
        println(coffeeDtos.first().name)
    }
}

data class CoffeeDto(
    val name: String
)

data class Dummy(
//    @get:JsonProperty("aName")
    @JvmField
    val aName: String,
//    @get:JsonProperty("bName")
    @JvmField
    var bName: String,
    var aaName: String? = null
) {
    fun getaaName(): String? = aaName
}
