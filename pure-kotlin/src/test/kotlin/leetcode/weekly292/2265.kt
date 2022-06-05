package leetcode.weekly292

import org.junit.jupiter.api.Test

/**
 * https://leetcode.com/contest/weekly-contest-292/problems/count-nodes-equal-to-average-of-subtree/
 */
@Deprecated("어렵다... 못풀겠음 ㅠ")
class `2265` {

    @Test
    fun leetcodeTest() {

        // given

        // when

        // then
    }
}

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */
fun averageOfSubtree(root: TreeNode?): Int {
    if (root == null) {
        return 0
    }

    return sumOfRoot(root, root.left, root.right, root.`val`, currentCount = 1, currentSum = root.`val`)
}

private fun sumOfRoot(
    root: TreeNode,
    left: TreeNode?,
    right: TreeNode?,
    rootValue: Int,
    currentCount: Int,
    currentSum: Int,
): Int {

    if (left == null && right == null) {

        if (currentCount == 1) {
            return 1
        }

        return if (rootValue == currentSum / currentCount) 1 else 0
    }

    var sum = currentSum
    var count = currentCount
    var result = 0

    if (left != null) {
        // root 기준 && left 기준
        // count += sumOfRoot(left, left.left, left.right, root.`val`, currentCount + 1, currentSum = currentSum + left.`val`)
        result += sumOfRoot(left, left.left, left.right, left.`val`, 1, currentSum = left.`val`)
        sum = left.`val`
        count += 1
    }

    if (right != null) {
        // root 기준 && right 기준
        // count += sumOfRoot(right, right.left, right.right, root.`val`, currentCount + 1, currentSum = currentSum + right.`val`)
        result += sumOfRoot(right, right.left, right.right, right                                                                                                   .`val`, 1, currentSum = right.`val`)
        sum += right.`val`
        count += 1
    }

    if (root.`val` == sum / count) {
        result += 1
    }

    return result
}

