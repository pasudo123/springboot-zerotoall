package leetcode.weekly291

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

/**
 * https://leetcode.com/contest/weekly-contest-291/
 */
class `2259` {
    @Test
    fun leetcodeTest() {
        removeDigit("123", '3') shouldBe "12"
        removeDigit("1231", '1') shouldBe "231"
        removeDigit("551", '5') shouldBe "51"
        removeDigit("133235", '3') shouldBe "13325"
    }
}

fun removeDigit(number: String, digit: Char): String {
    var result = "0"
    for (index in number.indices) {
        val findIndex = number.indexOf(digit, index)
        if (findIndex == -1) {
            break
        }

        val currentValue = (number.substring(0, findIndex) + number.substring(findIndex + 1, number.length))

        if (currentValue.count() > result.count()) {
            result = currentValue
        } else {
            for (subIndex in result.indices) {
                if (currentValue[subIndex] > result[subIndex]) {
                    result = currentValue
                    break
                } else if (currentValue[subIndex] < result[subIndex]) {
                    break
                }
            }
        }
    }

    return result
}