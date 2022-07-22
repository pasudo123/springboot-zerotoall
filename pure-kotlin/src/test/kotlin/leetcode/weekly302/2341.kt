package leetcode.weekly302

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2341` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {

        numberOfPairs(intArrayOf(1,3,2,1,3,2,2)) shouldBe intArrayOf(3,1)
        numberOfPairs(intArrayOf(1,1)) shouldBe intArrayOf(1,0)
        numberOfPairs(intArrayOf(0)) shouldBe intArrayOf(0,1)
    }
}

fun numberOfPairs(nums: IntArray): IntArray {
    val array = Array(101) { 0 }

    nums.forEach { num ->
        array[num]++
    }

    var pairCount = 0
    var size = 0

    array.forEach { element ->
        if (element == 0) {
            return@forEach
        }

        pairCount += element / 2
        size += element % 2
    }

    return intArrayOf(pairCount, size)
}