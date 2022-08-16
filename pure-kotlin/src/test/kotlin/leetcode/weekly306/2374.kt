package leetcode.weekly306

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2374` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {

//        edgeScore(intArrayOf(1, 0, 0, 0, 0, 7, 7, 5)) shouldBe 7
//        edgeScore(intArrayOf(2, 0, 0, 2)) shouldBe 0
//        edgeScore(intArrayOf(2, 0, 0, 1)) shouldBe 0
        edgeScore(intArrayOf(1, 0, 1, 1, 1)) shouldBe 1
    }
}

fun edgeScore(edges: IntArray): Int {

    val sumGroup = mutableMapOf<Int, Long>()

    edges.forEachIndexed { index, edge ->
        if (sumGroup.containsKey(edge)) {
            sumGroup[edge] = sumGroup[edge]!! + index.toLong()
        } else {
            sumGroup[edge] = index.toLong()
        }
    }

    return sumGroup.toList().sortedWith(
        compareBy<Pair<Int, Long>> { it.second }.thenByDescending { it.first }
    ).last().first
}