package leetcode.weekly283

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2195` {

    @Test
    fun leetcodeTest() {

        minimalKSum(intArrayOf(1,4,25,10,25), 2) shouldBe 5
        minimalKSum(intArrayOf(5, 6), 6) shouldBe 25
    }
}

fun minimalKSum(nums: IntArray, k: Int): Long {
    // 100000000
    var sum = 0L

    return sum
}