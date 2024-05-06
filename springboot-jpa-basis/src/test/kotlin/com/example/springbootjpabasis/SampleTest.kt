package com.example.springbootjpabasis

import org.junit.jupiter.api.Test

class SampleTest {

    @Test
    fun `알고리즘`() {
    }

    fun numberOfRightTriangles(grid: Array<IntArray>): Long {
        val rows = grid.size
        val cols = grid[0].size
        var result = 0L

        for (row in 0 until rows - 1) {
            val startPointRows = mutableListOf<Pair<Int, Int>>()

            for (col in 0 until cols - 1) {
                val point = grid[row][col]

                if (point != 1) {
                    continue
                }

                startPointRows.add(Pair(row, col))
            }

            result += startPointRows.sumOf { count(it, grid) }
        }

        return result
    }

    private fun count(pair: Pair<Int, Int>, grid: Array<IntArray>): Long {
        // left
        for (index in 0 until pair.first) {
            for (subIndex in 0 until pair.second) {
                if (grid[index][subIndex] == 0) {
                    continue
                }
            }

            for (subIndex in pair.second until grid[0].size) {
            }
        }

        // right
        for (index in (pair.first + 1) until grid.size) {
            for (subIndex in 0 until pair.second) {
            }

            for (subIndex in (pair.second + 1) until grid[0].size) {
            }
        }

        return 0L
    }
}
