package leetcode.weekly291

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.min

class `2260` {

    @Test
    fun leetcodeTest() {
        minimumCardPickup(intArrayOf(3, 4, 2, 3, 4, 7)) shouldBe 4
        minimumCardPickup(intArrayOf(1, 0, 5, 3)) shouldBe -1
    }
}

fun minimumCardPickup(cards: IntArray): Int {
    val existGroup = mutableMapOf<Int, Int>()
    var count = Int.MAX_VALUE

    cards.forEachIndexed { index, card ->
        if (existGroup.containsKey(card)) {
            count = min(count, index - existGroup[card]!! + 1)
            existGroup[card] = index
        } else {
            existGroup[card] = index
        }
    }

    if (count == Int.MAX_VALUE) {
        return -1
    }

    return count
}