package leetcode.weekly294

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode

class `2278` {

    @Test
    fun leetcodeTest() {
        percentageLetter("foobar", 'o') shouldBe 33
        percentageLetter("jjjj", 'k') shouldBe 0
    }
}

fun percentageLetter(s: String, letter: Char): Int {

    var hit = 0L

    s.forEach { char ->
        if (char == letter) {
            hit++
        }
    }

    return BigDecimal.valueOf(hit)
        .divide(BigDecimal.valueOf(s.length.toLong()), 2, RoundingMode.FLOOR)
        .multiply(BigDecimal.valueOf(100L))
        .toInt()
}