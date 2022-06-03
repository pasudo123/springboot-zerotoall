package leetcode.weekly293

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.max

@Deprecated("잘 모르겠음")
class `2274` {

    @Test
    fun leetcodeTest() {
        maxConsecutive(2, 9, intArrayOf(4, 6)) shouldBe 3
        maxConsecutive(6, 8, intArrayOf(7, 6, 8)) shouldBe 0
    }
}

fun maxConsecutive(bottom: Int, top: Int, special: IntArray): Int {

    val group = mutableSetOf<Int>()
    special.forEach { element ->
        group.add(element)
    }

    var result = 0
    var start = bottom

    for(floor in bottom..top) {
        if (group.contains(floor)) {
            result = max((floor - 1) - start + 1, result)
            start = floor + 1
        }

        if (floor == top) {
            result = max(floor - start + 1, result)
        }
    }

    return result
}