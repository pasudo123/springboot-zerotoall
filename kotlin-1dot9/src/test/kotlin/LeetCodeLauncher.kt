import kotlin.math.min
import kotlin.test.Test

class LeetCodeLauncher {

    @Test
    fun `leetcode 풀이`() {
        println(Solution().increasingTriplet(intArrayOf(0,4,2,1,0,-1,-3)))
    }
}

class Solution {
    fun increasingTriplet(nums: IntArray): Boolean {

        var first = Int.MAX_VALUE
        var second = Int.MAX_VALUE

        for (index in nums.indices) when {
            nums[index] <= first -> first = nums[index]
            nums[index] <= second -> second = nums[index]
            else -> return true
        }

        return false
    }
}