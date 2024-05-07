package com.example.springbootjpabasis.jackson

import com.example.springbootjpabasis.config.toJson
import com.example.springbootjpabasis.config.toObject
import org.junit.jupiter.api.Test

class JacksonTest {

    @Test
    fun `databind test`() {
        // 직렬화 : single lowercase 에는 문제가 있음. @get:JsonProperty or @JvmField 적용이 필요.
        val dummy = Dummy(aName = "hello", bName = "hi")
        println(dummy.toJson())

        // 역직렬화 : single lowercase 에는 문제가 없음
        val json = """
            {
                "aName": "hello-aName",
                "bName": "hello-bName"
            }
        """.trimIndent()

        println(json.toObject<Dummy>().toString())
    }
}

data class Dummy(
//    @get:JsonProperty("aName")
    @JvmField
    val aName: String,
//    @get:JsonProperty("bName")
    @JvmField
    var bName: String
)