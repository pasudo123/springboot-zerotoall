package leetcode.weekly295

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.max

class `2289` {

    @Test
    fun leetcodeTest() {
        totalSteps(intArrayOf(5,3,4,4,7,3,6,11,8,5,11)) shouldBe 3
        totalSteps(intArrayOf(4,5,7,7,13)) shouldBe 0
        totalSteps(intArrayOf(10,1,2,3,4,5,6,1,2,3)) shouldBe 6
        totalSteps(intArrayOf(7,11,1)) shouldBe 1
    }
}

fun totalSteps(nums: IntArray): Int {

    for(index in nums.indices) {

    }

    return 0
}

