package leetcode.weekly301

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2336` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {

        val smallestInfiniteSet = SmallestInfiniteSet()
        smallestInfiniteSet.addBack(2);    // 2 is already in the set, so no change is made.
        smallestInfiniteSet.popSmallest(); // return 1, since 1 is the smallest number, and remove it from the set.
        smallestInfiniteSet.popSmallest(); // return 2, and remove it from the set.
        smallestInfiniteSet.popSmallest(); // return 3, and remove it from the set.
        smallestInfiniteSet.addBack(1);    // 1 is added back to the set.
        smallestInfiniteSet.popSmallest(); // return 1, since 1 was added back to the set and
        // is the smallest number, and remove it from the set.
        smallestInfiniteSet.popSmallest(); // return 4, and remove it from the set.
        smallestInfiniteSet.popSmallest();
    }
}

class SmallestInfiniteSet {

    private val boolGroup = Array(1001) { true }

    init {
        boolGroup[0] = false
    }

    fun popSmallest(): Int {
        val res =  boolGroup.indexOf(true)
        boolGroup[res] = false
        return res
    }

    fun addBack(num: Int) {
        boolGroup[num] = true
    }
}
