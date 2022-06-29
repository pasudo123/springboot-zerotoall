package leetcode.weekly283

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2194` {

    @Test
    fun leetcodeTest() {
        cellsInRange("K1:L2") shouldBe listOf("K1","K2","L1","L2")
        cellsInRange("A1:F1") shouldBe listOf("A1","B1","C1","D1","E1","F1")
    }
}

fun cellsInRange(s: String): List<String> {
    val alphabets = ('A'..'Z').toMutableList()

    val startAlphabet = s.split(":").first()[0]
    val startNumber = s.split(":").first()[1].toString().toInt()
    val endAlphabet = s.split(":").last()[0]
    val endNumber = s.split(":").last()[1].toString().toInt()

    val result = mutableListOf<String>()

    var include = false
    for (index in alphabets.indices) {

        val currentChar = alphabets[index]

        if (currentChar == startAlphabet) {
            include = true
        }

        if (include) {
            for (number in startNumber..endNumber) {
                result.add("$currentChar$number")
            }
        }

        if (currentChar == endAlphabet) {
            break
        }
    }

    return result.toList()
}