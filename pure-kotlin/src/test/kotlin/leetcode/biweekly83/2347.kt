package leetcode.biweekly83

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2347` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {

    }
}

fun bestHand(ranks: IntArray, suits: CharArray): String {
    val current = suits.first()
    var isFlushed = true
    suits.forEach {
        if (current != it) {
            isFlushed = false
        }
    }

    if (isFlushed) {
        return "Flush"
    }

    var isPaired = false
    (1..13).forEach { number ->
        val filtered = ranks.filter { rank ->
            rank == number
        }

        if (filtered.size >= 3) {
            return "Three of a Kind"
        }

        if (filtered.size >= 2) {
            isPaired = true
        }
    }

    if (isPaired) {
        return "Pair"
    }

    return "High Card"
}