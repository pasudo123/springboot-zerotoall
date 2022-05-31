package leetcode.weekly295

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.max

class `2289` {

    @Test
    fun leetcodeTest() {
//        totalSteps(intArrayOf(5,3,4,4,7,3,6,11,8,5,11)) shouldBe 3
//        totalSteps(intArrayOf(4,5,7,7,13)) shouldBe 0
//        totalSteps(intArrayOf(10,1,2,3,4,5,6,1,2,3)) shouldBe 6
//        totalSteps(intArrayOf(7,11,1)) shouldBe 1
//        totalSteps(intArrayOf(5,11,7,8,11)) shouldBe 2
//        totalSteps(intArrayOf(7,14,4,14,13,2,6,13)) shouldBe 3
        totalSteps(intArrayOf(5,14,15,2,11,5,13,15)) shouldBe 3
    }
}

fun totalSteps(nums: IntArray): Int {

    val firstSubList = mutableListOf<Int>()
    val subList = mutableListOf<MutableList<Int>>()
    var startIndex = 0

    for(index in nums.indices) {
        firstSubList.add(nums[index])

        if (index < nums.size - 1 && nums[index] > nums[index + 1]) {
            startIndex = index + 1
            break
        }
    }

    if (startIndex == 0) {
        return 0
    }

    subList.add(firstSubList)
    var index = startIndex
    while (index < nums.size) {

        val elements = mutableListOf<Int>()

        while (index < nums.size - 1 && nums[index] <= nums[index + 1]) {
            elements.add(nums[index])
            index++
        }

        if (index >= 1 && nums[index - 1] < nums[index]) {
            elements.add(nums[index])
            subList.add(elements)
            index++
            continue
        }

        elements.add(nums[index])
        subList.add(elements)
        index++
    }

    var count = 0
    while (true) {
        var isSplit = false

        subList.mapIndexed { index, chunk ->
            if (index == 0) {
                chunk
            } else {
                isSplit = true
                split(subList[index - 1].last(), chunk)
            }
        }.flatten()

        if (isSplit.not()) {
            break
        }

        count++
    }

    // 계산

    return count
}

private fun split(lastValue: Int, splits: List<Int>): List<Int> {
    return splits.filter { element -> (lastValue < element) }.toList()
}

