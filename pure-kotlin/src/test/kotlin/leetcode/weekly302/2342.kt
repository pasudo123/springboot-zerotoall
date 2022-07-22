package leetcode.weekly302

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.math.max

class `2342` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {

        maximumSum(intArrayOf(18,43,36,13,7)) shouldBe 54
        maximumSum(intArrayOf(10,12,19,14)) shouldBe -1
    }
}

fun maximumSum(nums: IntArray): Int {

    val groupArray: MutableMap<Int, MutableList<Int>> = mutableMapOf()

    nums.forEach { num ->
        val numString = num.toString()

        val digitSum = numString.sumOf { Integer.parseInt(it.toString()) }

        if (groupArray.containsKey(digitSum).not()) {
            groupArray[digitSum] = mutableListOf(num)
        } else {
            groupArray[digitSum]?.add(num)
        }
    }

    var result = -1

    groupArray.keys.forEach { key ->
        val descGroup = groupArray[key]!!.sortedDescending()

        if (descGroup.size <= 1) {
            return@forEach
        }

        result = max(result, (descGroup[0] + descGroup[1]))
    }

    return result
}