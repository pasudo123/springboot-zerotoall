package leetcode.weekly290

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

@Deprecated("다시 풀기")
class `2249` {

    @Test
    fun leetcodeTest() {

//        countLatticePoints(arrayOf(intArrayOf(2, 2, 1))) shouldBe 5
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
        for (size in 1..r) {
            group.add("${x}&${y-size}")
            group.add("${x}&${y+size}")
            group.add("${x-size}&${y}")
            group.add("${x+size}&${y}")

            if (size < r) {
                group.add("${x - size}&${y - size}")
                group.add("${x + size}&${y - size}")
                group.add("${x - size}&${y + size}")
                group.add("${x + size}&${y + size}")
            }
        }
    }

    return group.size
}

