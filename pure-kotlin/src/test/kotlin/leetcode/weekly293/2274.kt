package leetcode.weekly293

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.max

class `2274` {

    @Test
    fun leetcodeTest() {
        maxConsecutive(2, 9, intArrayOf(4, 6)) shouldBe 3
        maxConsecutive(6, 8, intArrayOf(7, 6, 8)) shouldBe 0
    }
}

fun maxConsecutive(bottom: Int, top: Int, special: IntArray): Int {

    var maxCount = 0
    special.sort()

    for (index in special.indices) {
        if (index == 0 || special[index] - special[index - 1] == 1) {
            continue
        }

        maxCount = max(maxCount, special[index] - special[index - 1] - 1)
    }

    maxCount = max(maxCount, special.first() - bottom)
    maxCount = max(maxCount, top - special.last())

    return maxCount
}