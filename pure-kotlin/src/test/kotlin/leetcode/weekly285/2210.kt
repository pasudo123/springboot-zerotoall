package leetcode.weekly285

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2210` {

    @Test
    fun leetcodeTest() {
        countHillValley(intArrayOf(2,4,1,1,6,5)) shouldBe 3
        countHillValley(intArrayOf(6,6,5,5,4,1)) shouldBe 0
    }
}

fun countHillValley(nums: IntArray): Int {

    val newNums = mutableListOf<Int>()
    nums.forEachIndexed { index, element ->
        if (index == 0 || index == nums.size - 1) {
            newNums.add(element)
        } else {
            if (newNums.last() == element) {

            } else {
                newNums.add(element)
            }
        }
    }

    if (newNums.size <= 2) {
        return 0
    }

    var lastValue = -1
    var count = 0

    for (index in 1 until newNums.size - 1) {

        // hill
        if (newNums[index] > newNums[index - 1] && newNums[index] > newNums[index + 1]) {
            if (newNums[index] != lastValue) {
                count += 1
                lastValue = newNums[index]
            }
            continue
        }

        // valley
        if (newNums[index] < newNums[index - 1] && newNums[index] < newNums[index + 1]) {
            if (newNums[index] != lastValue) {
                count += 1
                lastValue = newNums[index]
            }
            continue
        }
    }

    return count
}