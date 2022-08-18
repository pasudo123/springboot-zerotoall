package leetcode.weekly305

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2367` {
    
    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {
    
        // given
        
        // when
        
        // then
    }
}

fun arithmeticTriplets(nums: IntArray, diff: Int): Int {

    var count = 0

    for (i in nums.indices) {
        for (j in i + 1 until nums.size) {
            val firstDiff = nums[j] - nums[i]
            if (firstDiff > diff) {
                break
            }

            for (k in j + 1 until nums.size) {
                val secondDiff = nums[k] - nums[j]
                if (secondDiff > diff) {
                    break
                }

                if (firstDiff == secondDiff && firstDiff == diff) {
                    count++
                }
            }
        }
    }

    return count
}