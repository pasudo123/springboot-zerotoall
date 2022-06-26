package leetcode.weekly284

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.abs

class `2200` {

    @Test
    fun leetcodeTest() {
        findKDistantIndices(intArrayOf(3, 4, 9, 1, 3, 9, 5), 9, 1) shouldBe listOf(1, 2, 3, 4, 5, 6)
        findKDistantIndices(intArrayOf(2, 2, 2, 2, 2), 2, 2) shouldBe listOf(0, 1, 2, 3, 4)
    }
}

fun findKDistantIndices(nums: IntArray, key: Int, k: Int): List<Int> {

    val keyIndexes = mutableListOf<Int>()

    nums.forEachIndexed { index, element ->
        if (element == key) {
            keyIndexes.add(index)
        }
    }

    val results = mutableListOf<Int>()

    for (index in nums.indices) {
        for (subIndex in keyIndexes.indices) {
            if (abs(index - keyIndexes[subIndex]) <= k) {
                results.add(index)
                break
            }
        }
    }

    return results.sorted().toList()
}
