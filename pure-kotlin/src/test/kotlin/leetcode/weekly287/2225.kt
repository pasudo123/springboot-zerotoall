package leetcode.weekly287

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2225` {

    @Test
    fun leetcodeTest() {
        findWinners(arrayOf(
            intArrayOf(1, 3), intArrayOf(2, 3), intArrayOf(3, 6),
            intArrayOf(5, 6), intArrayOf(5, 7), intArrayOf(4, 5),
            intArrayOf(4, 8), intArrayOf(4, 9), intArrayOf(10, 4),
            intArrayOf(10, 9)
        )) shouldBe listOf(listOf(1, 2, 10), listOf(4, 5, 7, 8))
    }
}

fun findWinners(matches: Array<IntArray>): List<List<Int>> {
    val losers = mutableMapOf<Int, Int>()

    val allPlayers = matches.map { match ->
        match.toList()
    }.flatten().distinct().toMutableSet()

    matches.forEach { match ->
        allPlayers.remove(match.last())
        losers[match.last()] = if (losers[match.last()] == null) 1 else losers[match.last()]!! + 1
    }

    val resultLosers = losers.keys.filter { loserPlayer ->
        losers[loserPlayer]!! == 1
    }

    return listOf(allPlayers.sorted(), resultLosers.sorted())
}