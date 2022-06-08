package leetcode.weekly290

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2248` {

    @Test
    fun leetcodeTest() {
        intersection(arrayOf(intArrayOf(3, 1, 2, 4, 5), intArrayOf(1, 2, 3, 4), intArrayOf(3, 4, 5, 6))) shouldBe listOf(3, 4)
        intersection(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6))) shouldBe listOf(3, 4)
    }
}

fun intersection(nums: Array<IntArray>): List<Int> {

    val fullCount = nums.size
    val group = mutableMapOf<Int, Int>()

    nums.forEach { num ->
        num.forEach { element ->
            if (group.containsKey(element)) {
                group[element] = group[element]!! + 1
            } else {
                group[element] = 1
            }
        }
    }

    val result = mutableListOf<Int>()
    group.keys.forEach { key ->
        if (group[key] == fullCount) {
            result.add(key)
        }
    }

    return result.sorted()
}