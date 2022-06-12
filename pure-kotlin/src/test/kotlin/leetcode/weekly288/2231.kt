package leetcode.weekly288

import io.kotest.assertions.timing.eventually
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldHave
import org.junit.jupiter.api.Test

class `2231` {

    @Test
    fun leetcodeTest() {
        largestInteger(1234) shouldBe 3412
        largestInteger(65875) shouldBe 87655
        largestInteger(247) shouldBe 427
    }
}

fun largestInteger(num: Int): Int {
    val numsString = num.toString()
    val evenArray = mutableListOf<Int>()
    val oddArray = mutableListOf<Int>()
    numsString.forEach { char ->
        if (char.toString().toInt() % 2 == 0) {
            evenArray.add(char.toString().toInt())
        } else {
            oddArray.add(char.toString().toInt())
        }
    }

    evenArray.sortDescending()
    oddArray.sortDescending()
    var originIndex = 0
    var evenIndex = 0
    var oddIndex = 0

    var result = ""

    while (evenIndex < evenArray.size && oddIndex < oddArray.size) {
        if (numsString[originIndex].toString().toInt() % 2 == 0) {
            result = "$result${evenArray[evenIndex++]}"
        } else {
            result = "$result${oddArray[oddIndex++]}"
        }

        originIndex++
    }

    while (evenIndex < evenArray.size) {
        result = "$result${evenArray[evenIndex++]}"
    }

    while (oddIndex < oddArray.size) {
        result = "$result${oddArray[oddIndex++]}"
    }

    return result.toInt()
}
