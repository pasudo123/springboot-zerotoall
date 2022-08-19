package leetcode.weekly305

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * https://leetcode.com/contest/weekly-contest-305/problems/reachable-nodes-with-restrictions/
 */
@Deprecated("어렵다 다시 풀기")
class `2368` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {
        reachableNodes(7, arrayOf(intArrayOf(0, 1), intArrayOf(1, 2), intArrayOf(3, 1), intArrayOf(4, 0), intArrayOf(0, 5), intArrayOf(5, 6)), intArrayOf(4, 5)) shouldBe 4
//        reachableNodes(7, arrayOf(intArrayOf(0, 1), intArrayOf(0, 2), intArrayOf(0, 5), intArrayOf(0, 4), intArrayOf(3, 2), intArrayOf(6, 5)), intArrayOf(4, 2, 1)) shouldBe 3
    }
}

fun reachableNodes(n: Int, edges: Array<IntArray>, restricted: IntArray): Int {

    val visited = mutableSetOf<Int>()
    val group = mutableMapOf<Int, MutableSet<Int>>()

    (0..n).forEach {
        group[it] = mutableSetOf()
    }

    edges.forEach { edge ->
        group[edge.first()]!!.add(edge.last())
        group[edge.last()]!!.add(edge.first())
    }

    return recursive(0, group, restricted, visited, 1)
}

private fun recursive(
    startNodeNumber: Int,
    group: Map<Int,MutableSet<Int>>,
    restricted: IntArray,
    visited: MutableSet<Int>,
    count: Int
): Int {

    // 해당노드는 제한된 노드
    if (restricted.contains(startNodeNumber)) {
        return -count
    }

    val toBeNodes = group[startNodeNumber]!!

    val filteredToBeNodes = toBeNodes.filter { node ->
        visited.contains(node).not()
    }

    // 모두 방문
    if (filteredToBeNodes.isEmpty()) {
        return count
    }

    var subCount = 0
    visited.add(startNodeNumber)
    filteredToBeNodes.forEach { node ->
        subCount += recursive(node, group, restricted, visited, count + 1)
    }

    return 1
}