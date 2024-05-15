package com.example.springbootjpabasis

import org.junit.jupiter.api.Test
import kotlin.math.abs

class SampleTest {

    @Test
    fun `알고리즘`() {
    }

    fun findPermutationDifference(s: String, t: String): Int {
        val sum = mutableMapOf<Char, Int>()

        s.forEachIndexed { index, char ->
            sum[char] = index
        }

        t.forEachIndexed { index, char ->
            val currentIndex = sum[char]!!
            sum[char] = abs(currentIndex - index)
        }

        return sum.values.sum()
    }
}
