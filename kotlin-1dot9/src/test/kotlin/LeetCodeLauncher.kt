import kotlin.math.min
import kotlin.test.Test

class LeetCodeLauncher {

    @Test
    fun `leetcode 풀이`() {
        Solution().run { println(minSizeSubarray(nums = intArrayOf(1,2,2,2,1,2,1,2,1,2,1), 83)) }
    }
}

class Solution {
    fun minSizeSubarray(nums: IntArray, target: Int): Int {
        val infiArray = nums + nums
        var result = Int.MAX_VALUE
        infiArray.forEachIndexed { index, _ ->
            result = min(infiArray.checkLength(index, target), result)
        }

        if (result == Int.MAX_VALUE) return -1
        return result
    }

    private fun IntArray.checkLength(index: Int, target: Int): Int {
        var sum = 0
        for (currentIndex in index until this.count()) {
            if (sum == target) {
                val previousIndex = currentIndex - 1
                return (previousIndex - index + 1)
            }

            if (sum > target) {
                break
            }

            sum += this[currentIndex]
        }

        return Int.MAX_VALUE
    }
}