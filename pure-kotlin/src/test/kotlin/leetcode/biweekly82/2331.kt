package leetcode.biweekly82

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2331` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {

    }
}

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

fun evaluateTree(root: TreeNode?): Boolean {

    if (root?.`val` == 2) {
        return evaluateTree(root.left) || evaluateTree(root.right)
    }

    if (root?.`val` == 3) {
        return evaluateTree(root.left) && evaluateTree(root.right)
    }

    if (root?.`val` == 0) {
        return false
    }

    if (root?.`val` == 1) {
        return true
    }

    return false
}