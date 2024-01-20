package com.example.springbootbasis.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.util.ConcurrentLruCache
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class UrlEncoderUtilTest {

     // var localCache = ConcurrentLruCache<String, String>(100) { TODO("") }

    @Test
    fun `urlEncode 를 수행한다`() {

        // val newLocalCache = ConcurrentHashMap<String, String>()

        val plain = "test/sample/online"

        plain.urlEncode() shouldBe "test%2Fsample%2Fonline"
        plain.urlDecode() shouldBe "test/sample/online"
    }
}