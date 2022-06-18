package leetcode.weekly286

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

@Deprecated("다시 풀기 모르겠음")
class `2216` {

    @Test
    fun leetcodeTest() {
        minDeletion(intArrayOf(1, 1, 2, 3, 5)) shouldBe 1
        minDeletion(intArrayOf(1, 1, 2, 2, 3, 3)) shouldBe 2
    }
}

fun minDeletion(nums: IntArray): Int {

    if (nums.size % 2 == 0) {
        var isGood = true
        for (index in 0 until nums.size - 1) {
            if (index % 2 == 0 && nums[index] == nums[index + 1]) {
                isGood = false
                break
            }
        }

        if (isGood) {
            return 0
        }
    }

    var currentSize = nums.size
    var result = mutableListOf<Int>()

    for (index in 0 until nums.size - 1) {
        if (index % 2 != 0) {
            continue
        }

        if (nums[index] == nums[index + 1]) {
        }
    }


    return 0
}