package leetcode.weekly299

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2319` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {

    }
}

fun checkXMatrix(grid: Array<IntArray>): Boolean {
    return checkRecursive(grid, 0, 0, grid.size)
}

private fun checkRecursive(grid: Array<IntArray>, row: Int, col: Int, size: Int): Boolean {

    if (size <= 0) {
        return true
    }

    if (grid[row][col] == 0) {
        return false
    }

    if (grid[row][col + size - 1] == 0) {
        return false
    }

    if (grid[row + size - 1][col] == 0) {
        return false
    }

    if (grid[row + size - 1][col + size - 1] == 0) {
        return false
    }

    var currentRow = row + 1
    var currentCol = col + 1
    while (currentCol < col + size - 1) {
        if (grid[row][currentCol] != 0) {
            return false
        }

        if (grid[row + size - 1][currentCol] != 0) {
            return false
        }
        currentCol++
    }

    while (currentRow < row + size - 1) {
        if (grid[currentRow][col] != 0) {
            return false
        }

        if (grid[currentRow][col + size - 1] != 0) {
            return false
        }

        currentRow++
    }

    return checkRecursive(grid, row + 1, col + 1, size - 2)
}