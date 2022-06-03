package leetcode.weekly292

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.max

/**
 * https://leetcode.com/contest/weekly-contest-292/problems/largest-3-same-digit-number-in-string/
 */
class `2264` {

    @Test
    fun leetcodeTest() {
        largestGoodInteger("6777133339") shouldBe "777"
        largestGoodInteger("2300019") shouldBe "000"
        largestGoodInteger("42352338") shouldBe ""
    }
}

fun largestGoodInteger(num: String): String {

    var result: Long = -1

    for(index in 0 until num.count() - 2) {
        if (num[index] != num[index + 1]
            || num[index + 1] != num[index + 2]
            || num[index] != num[index + 2]) {
            continue
        }

        val digit = "${num[index]}".toLong()
        result = max(result, digit)
    }

    return if (result == -1L) "" else "$result$result$result"
}