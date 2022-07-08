package leetcode.weekly280

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

/**
 * 졸리다. : https://leetcode.com/contest/weekly-contest-280/problems/minimum-operations-to-make-the-array-alternating/
 */
class `2170` {
    
    @Test
    fun leetcodeTest() {
        minimumOperations(intArrayOf(3,1,3,2,4,3)) shouldBe 3
        minimumOperations(intArrayOf(1,2,2,2,2)) shouldBe 2
    }
}

fun minimumOperations(nums: IntArray): Int {

    val evenGroup = mutableMapOf<Int, Int>()
    val oddGroup = mutableMapOf<Int, Int>()

    nums.forEachIndexed { index, element ->
        if (index % 2 == 0) {
            evenGroup[element] = evenGroup[element]?.plus(1) ?: 1
            return@forEachIndexed
        }

        oddGroup[element] = oddGroup[element]?.plus(1) ?: 1
    }

    val sortedEvenGroup = evenGroup.toList().sortedBy { it.second }
    val sortedOddGroup = oddGroup.toList().sortedBy { it.second }

    // 가장 많은 숫자의 서로 쌍으로 잡힌 값이 다른 경우
    if (sortedEvenGroup.first().first != sortedOddGroup.first().first) {
        val evenCount = if (sortedEvenGroup.size >= 2) {
            sortedEvenGroup[1].first
        } else {
            // size 가 1인거는 짝수인덱스에서 교체할 값이 없음을 의미
            0
        }

        val oddCount = if (sortedOddGroup.size >= 2) {
            sortedOddGroup[1].first
        } else {
            // size 가 1인거는 홀수인덱스에서 교체할 값이 없음을 의미
            0
        }

        return (evenCount + oddCount)
    }

    var count = 0
    // 숫자 쌍의 값이 서로 동일한 경우
    sortedEvenGroup

    return 0
}