package leetcode.weekly289

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2243` {

    @Test
    fun leetcodeTest() {
        digitSum("11111222223", 3) shouldBe "135"
        digitSum("00000000", 3) shouldBe "000"
    }
}

fun digitSum(s: String, k: Int): String {
    if (s.count() <= k) {
        return s
    }

    var sumDigit = ""
    s.chunked(k).forEach{ chunk ->
        println("current sum : $sumDigit, $chunk")
        val sum = chunk
            .map { it.toString().toInt() }
            .reduce { total, digit -> total + digit }

        sumDigit = "$sumDigit$sum"
    }

    return digitSum(sumDigit, k)
}