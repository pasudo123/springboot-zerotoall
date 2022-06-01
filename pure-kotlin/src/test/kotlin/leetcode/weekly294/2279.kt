package leetcode.weekly294

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2279` {

    @Test
    fun leetcodeTest() {
        maximumBags(intArrayOf(2,3,4,5), intArrayOf(1,2,4,4), 2) shouldBe 3
        maximumBags(intArrayOf(10,2,2), intArrayOf(2,2,0), 100) shouldBe 3
    }
}

fun maximumBags(capacity: IntArray, rocks: IntArray, additionalRocks: Int): Int {

    var count = 0
    val remainBagSize = mutableListOf<Int>()

    for (index in capacity.indices) {
        val remainSize = capacity[index] - rocks[index]
        remainBagSize.add(remainSize)
    }

    var subtractRocks = additionalRocks
    remainBagSize.sorted().forEach { currentSize ->
        if (currentSize == 0) {
            count++
            return@forEach
        }

        if (subtractRocks >= 1 && subtractRocks >= currentSize) {
            subtractRocks -= currentSize
            count++
        }
    }

    return count
}