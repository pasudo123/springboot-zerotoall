package leetcode.weekly283

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigInteger

class `2195` {

    @Test
    fun leetcodeTest() {
//        minimalKSum(intArrayOf(1,4,25,10,25), 2) shouldBe 5
        minimalKSum(intArrayOf(5, 6), 6) shouldBe 25
    }
}

fun minimalKSum(nums: IntArray, k: Int): Long {
    val sortedDistinctNums = nums.distinct().sorted()
    val pairs: MutableList<Pair<Long, Long>> = mutableListOf()

    if (sortedDistinctNums.first() != 1) {
        pairs.add(Pair(1L, sortedDistinctNums.first() - 1L))
    }

    // 쌍을 찾는다.
    for (index in sortedDistinctNums.indices) {

        val prev = sortedDistinctNums[index]

        if (index == sortedDistinctNums.size - 1) {
            // max 값까지 세팅
            pairs.add(Pair(prev + 1L, 1000000000L))
            continue
        }

        val next = sortedDistinctNums[index + 1]

        if (next - prev == 1) {
            continue
        }

        pairs.add(Pair(prev + 1L, next - 1L))
    }

    var sum = 0L
    var currentCount = k
    pairs.forEach { pair ->

        if (currentCount == 0) {
            return sum
        }

        val prev = pair.first
        val next = pair.second

        val count = (next - prev + 1).toInt()

        if (currentCount >= count) {
            currentCount -= count
            val oneToNextSum = BigInteger.valueOf(next).multiply(BigInteger.valueOf(next + 1L)).divide(BigInteger.valueOf(2L))
            val oneToPrevMinOneSum = BigInteger.valueOf(prev - 1L).multiply(BigInteger.valueOf(prev)).divide(BigInteger.valueOf(2L))
            sum += oneToNextSum.minus(oneToPrevMinOneSum).toLong()
            return@forEach
        }

        // currentCount < count
        // prev..(prev + currentCount - 1)
        val oneToPrevPlusCurrentCountSum = BigInteger.valueOf(prev + currentCount - 1).multiply(BigInteger.valueOf(prev + currentCount)).divide(BigInteger.valueOf(2L))
        val oneToPrevMinOneSum = BigInteger.valueOf(prev - 1L).multiply(BigInteger.valueOf(prev)).divide(BigInteger.valueOf(2L))
        sum += oneToPrevPlusCurrentCountSum.minus(oneToPrevMinOneSum).toLong()
        currentCount = 0
    }

    return sum
}