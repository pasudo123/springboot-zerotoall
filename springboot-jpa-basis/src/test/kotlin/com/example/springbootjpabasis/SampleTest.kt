package com.example.springbootjpabasis

import org.junit.jupiter.api.Test

class SampleTest {

    @Test
    fun `알고리즘`() {
        canMakeSquare(grid = arrayOf(
            charArrayOf('B','W','B'),
            charArrayOf('W','B','W'),
            charArrayOf('B','W','B'),
            ))
    }

    fun canMakeSquare(grid: Array<CharArray>): Boolean {

        for (row in 0 until (grid.size - 1)) {
            var blackCount = 0
            var whiteCount = 0

            for (col in 0 until (grid[row].size - 1)) {
                val color1 = grid[row][col]
                val color2 = grid[row][col + 1]
                val color3 = grid[row + 1][col]
                val color4 = grid[row + 1][col + 1]

                if (color1 == 'B') blackCount++ else whiteCount++
                if (color2 == 'B') blackCount++ else whiteCount++
                if (color3 == 'B') blackCount++ else whiteCount++
                if (color4 == 'B') blackCount++ else whiteCount++

                if (blackCount == 4 || whiteCount == 4) {
                    return true
                }

                if (blackCount == 3 && whiteCount == 1) {
                    return true
                }

                if (blackCount == 1 && whiteCount == 3) {
                    return true
                }

                blackCount = 0
                whiteCount = 0
            }
        }

        return false
    }
}