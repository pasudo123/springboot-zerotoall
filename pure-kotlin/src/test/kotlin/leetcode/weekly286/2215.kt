package leetcode.weekly286

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2215` {

    @Test
    fun leetcodeTest() {
        findDifference(intArrayOf(1, 2, 3), intArrayOf(2, 4, 6)) shouldBe listOf(listOf(1, 3), listOf(4, 6))
        findDifference(intArrayOf(1, 2, 3, 3), intArrayOf(1, 1, 2, 2)) shouldBe listOf(listOf(3), listOf())
    }
}

fun findDifference(nums1: IntArray, nums2: IntArray): List<List<Int>> {

    val nums1Group = nums1.toMutableSet()
    nums2.forEach {
        nums1Group.remove(it)
    }

    val nums2Group = nums2.toMutableSet()
    nums1.forEach {
        nums2Group.remove(it)
    }

    return listOf(nums1Group.toList(), nums2Group.toList())
}