package leetcode.weekly280

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2169` {

    @Test
    fun leetcodeTest() {
        countOperations(2, 3) shouldBe 3
        countOperations(10, 10) shouldBe 1
    }
}

fun countOperations(num1: Int, num2: Int): Int {

    if (num1 == 0 || num2 == 0) {
        return 0
    }

    return if (num1 >= num2) {
        1 + countOperations(num1 - num2, num2)
    } else {
        1 + countOperations(num1, num2 - num1)
    }
}