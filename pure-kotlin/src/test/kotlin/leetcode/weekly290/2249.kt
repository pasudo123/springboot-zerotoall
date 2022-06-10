package leetcode.weekly290

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

@Deprecated("다시 풀기")
class `2249` {

    @Test
    fun leetcodeTest() {

        countLatticePoints(arrayOf(intArrayOf(2, 2, 1))) shouldBe 5
        countLatticePoints(arrayOf(intArrayOf(2, 2, 2), intArrayOf(3, 4, 1))) shouldBe 16
    }
}

fun countLatticePoints(circles: Array<IntArray>): Int {
    val group = mutableSetOf<String>()

    circles.forEach { circle ->
        val y = circle[0]
        val x = circle[1]
        val r = circle[2]

        for (currentY in (y-r)..(y+r)) {
            for (currentX in (x-r)..(x+r)) {
                val diagonal = abs(currentY - y).toDouble().pow(2) + abs(currentX - x).toDouble().pow(2)

                if (sqrt(diagonal) <= r) {
                    group.add("$currentY&$currentX")
                }
            }
        }
    }

    return group.size
}
