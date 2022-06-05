package leetcode.weekly296

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.math.min

class `2293` {

    @Test
    fun leetcodeTest() {
        minMaxGame(intArrayOf(1,3,5,2,4,8,2,2)) shouldBe 1
        minMaxGame(intArrayOf(3)) shouldBe 3
    }
}

fun minMaxGame(nums: IntArray): Int {

    if (nums.size == 1) {
        return nums.first()
    }

    val result = mutableListOf<Int>()
    var currentIndex = 0

    while (currentIndex < nums.size / 2) {
        if (currentIndex % 2 == 0) {
            result.add(min(nums[2 * currentIndex], nums[2 * currentIndex + 1]))
        } else {
            result.add(max(nums[2 * currentIndex], nums[2 * currentIndex + 1]))
        }

        currentIndex++
    }

    return minMaxGame(result.toIntArray())
}