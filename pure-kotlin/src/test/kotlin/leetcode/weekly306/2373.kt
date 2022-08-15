package leetcode.weekly306

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2373` {
    
    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {

    }
}

fun largestLocal(grid: Array<IntArray>): Array<IntArray> {

    val n = grid.size
    val newSize = n - 2
    val elements = mutableListOf<Int>()

    for (row in grid.indices) {
        for (col in grid[row].indices) {
            val maxValue = getMaxValueOrReturn(row, col, grid)
            if (maxValue == -1) {
                break
            }
            elements.add(maxValue)
        }
    }

    val newGrid = Array(newSize) { IntArray(newSize) }
    var currentIndex = 0

    for (row in 0 until newSize) {
        for (col in 0 until newSize) {
            newGrid[row][col] = elements[currentIndex++]
        }
    }

    return newGrid
}

private fun getMaxValueOrReturn(row: Int, col: Int, grid: Array<IntArray>): Int {

    var max = -1

    if (row + 2 >= grid.size || col + 2 >= grid.size) {
        return max
    }

    for (currentRow in row..(row + 2)) {
        for (currentCol in col..(col + 2)) {
            max = max.coerceAtLeast(grid[currentRow][currentCol])
        }
    }

    return max
}