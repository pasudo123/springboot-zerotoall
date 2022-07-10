package leetcode.weekly279

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.PriorityQueue

class `2164` {

    @Test
    fun leetcodeTest() {
//        sortEvenOdd(intArrayOf(4,1,2,3)) shouldBe intArrayOf(2,3,4,1)
//        sortEvenOdd(intArrayOf(2,1)) shouldBe intArrayOf(2,1)
        sortEvenOdd(intArrayOf(36,45,32,31,15,41,9,46,36,6,15,16,33,26,27,31,44,34)) shouldBe intArrayOf(9,46,15,45,15,41,27,34,32,31,33,31,36,26,36,16,44,6)
    }
}

fun sortEvenOdd(nums: IntArray): IntArray {

    val evenPq = PriorityQueue<Int>()
    val oddPq = PriorityQueue<Int>(reverseOrder())

    nums.forEachIndexed { index, num ->
        if (index % 2 == 0) {
            evenPq.add(num)
        } else {
            oddPq.add(num)
        }
    }

    val result = mutableListOf<Int>()
    nums.forEachIndexed { index, num ->
        if (index % 2 == 0) {
            result.add(evenPq.poll())
        } else {
            result.add(oddPq.poll())
        }
    }

    return result.toIntArray()
}