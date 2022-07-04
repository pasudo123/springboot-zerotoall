package leetcode.weekly282

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.abs

class `2186` {

    @Test
    fun leetcodeTest() {
        minSteps("leetcode", "coats") shouldBe 5
    }
}

private fun minSteps(s: String, t: String): Int {
    val sGroup = mutableMapOf<Char, Long>()

    s.forEach { element ->
        if (sGroup.containsKey(element)) {
            sGroup[element] = sGroup[element]!! + 1L
        } else {
            sGroup[element] = 1L
        }
    }

    val tGroup = mutableMapOf<Char, Long>()
    t.forEach { element ->
        if (tGroup.containsKey(element)) {
            tGroup[element] = tGroup[element]!! + 1L
        } else {
            tGroup[element] = 1L
        }
    }

    var sum = 0L
    sGroup.keys.forEach { element ->
        if (tGroup.containsKey(element)) {
            sum += abs(sGroup[element]!! - tGroup[element]!!)
            tGroup.remove(element)
        } else {
            sum += sGroup[element]!!
        }
    }

    tGroup.keys.forEach { element ->
        sum += tGroup[element]!!
    }

    return sum.toInt()
}