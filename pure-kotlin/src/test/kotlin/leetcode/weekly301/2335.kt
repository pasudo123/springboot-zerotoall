package leetcode.weekly301

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2335` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {
        fillCups(intArrayOf(1, 4, 2)) shouldBe 4
        fillCups(intArrayOf(5, 4, 4)) shouldBe 7
        fillCups(intArrayOf(5, 0, 0)) shouldBe 5
    }
}

fun fillCups(amount: IntArray): Int {

    val sum = amount.sum()
    if (sum == 0) {
        return 0
    }

    amount.sortDescending()

    if (amount[0] >= 1 && amount[1] >= 1) {
        amount[0] -= 1
        amount[1] -= 1
        return 1 + fillCups(amount)
    }

    if (amount[0] >= 1) {
        amount[0] -= 1
        return 1 + fillCups(amount)
    }

    return 0
}