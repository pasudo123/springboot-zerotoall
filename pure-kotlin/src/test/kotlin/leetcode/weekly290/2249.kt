package leetcode.weekly290

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

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

        group.add("$y&$x")

        if (r == 1) {
            group.add("${x}&${y - 1}")
            group.add("${x}&${y + 1}")
            group.add("${x - 1}&${y}")
            group.add("${x + 1}&${y}")
            return@forEach
        }

        for (size in 1 until r) {
            val pairs = listOf(
                Pair(size, 0), Pair(-size, 0),
                Pair(0, size), Pair(0, -size),
                Pair(size, size), Pair(size, -size),
                Pair(-size, size), Pair(-size, -size)
            )

            pairs.forEach { pair ->
                group.add("${y + pair.first}&${x + pair.second}")
            }
        }

        group.add("${y - r}&$x")
        group.add("${y + r}&$x")
        group.add("${y}&${x-r}")
        group.add("${y}&${x+r}")
    }

    return group.size
}

