package leetcode.weekly286

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

/**
 * https://leetcode.com/contest/weekly-contest-286/problems/minimum-deletions-to-make-array-beautiful/
 */
@Deprecated("다시 풀기 모르겠음")
class `2216` {

    @Test
    fun leetcodeTest() {
        minDeletion(intArrayOf(1, 1, 2, 3, 5)) shouldBe 1
        minDeletion(intArrayOf(1, 1, 2, 2, 3, 3)) shouldBe 2
    }
}

fun minDeletion(nums: IntArray): Int {

    var isChange = true
    var count = 0
    val prevResult = nums.toMutableList()
    val nextResult = mutableListOf<Int>()

    while(true) {

        for (index in prevResult.indices step 2) {
            val result = recursive(prevResult, index)
            nextResult.addAll(result)

            if (result.size == 0) {
                break
            }

            if (result.size == 1) {
                val startIndex = if (index + 2 >= prevResult.size + 1) prevResult.size else index + 2
                nextResult.addAll(prevResult.subList(startIndex, prevResult.size))
                break
            }
        }

        if (prevResult.size == nextResult.size && prevResult.containsAll(nextResult)) {
            isChange = false
        }

        count += (prevResult.size - nextResult.size)

        if (isChange.not()) {
            if (nextResult.size % 2 == 1) {
                count += 1
            }

            break
        }

        prevResult.clear()
        prevResult.addAll(nextResult)
        nextResult.clear()
    }

    return count
}

private fun recursive(nums: MutableList<Int>, currentIndex: Int): MutableList<Int> {

    if (currentIndex >= nums.size) {
        return mutableListOf()
    }

    if (currentIndex + 1 >= nums.size) {
        return mutableListOf(nums[currentIndex])
    }

    if (nums[currentIndex] == nums[currentIndex + 1]) {
        return mutableListOf(nums[currentIndex])
    }

    return mutableListOf(nums[currentIndex], nums[currentIndex + 1])
}