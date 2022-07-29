package leetcode.biweekly83

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2348` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {
        zeroFilledSubarray(intArrayOf(1,3,0,0,2,0,0,4)) shouldBe 6
        zeroFilledSubarray(intArrayOf(0,0,0,2,0,0)) shouldBe 9
        zeroFilledSubarray(intArrayOf(0,1,0,0,1,0,0,0)) shouldBe 10
    }
}

fun zeroFilledSubarray(nums: IntArray): Long {

    // 1 ~ n 까지의 합
    val group = mutableListOf<Int>()
    var count = 0
    var prev = nums.first()

    if (prev == 0) {
        count++
    }

    for (index in 1 until nums.size) {
        val current = nums[index]

        if (current == 0) {
            if (prev == 0) {
                count++
                continue
            }

            count++
            prev = current
            continue
        }

        if (count >= 1) {
            group.add(count)
        }

        count = 0
        prev = current
    }

    if (count >= 1) {
        group.add(count)
    }


    return group.sumOf {it.toLong() * (it.toLong() + 1) / 2 }
}