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
        reachableNodes(7, arrayOf(intArrayOf(0, 1), intArrayOf(0, 2), intArrayOf(0, 5), intArrayOf(0, 4), intArrayOf(3, 2), intArrayOf(6, 5)), intArrayOf(4, 2, 1)) shouldBe 3
    }
}

val visited = mutableSetOf<Int>()

fun reachableNodes(n: Int, edges: Array<IntArray>, restricted: IntArray): Int {

    val group = mutableMapOf<Int, MutableSet<Int>>()

    (0..n).forEach {
        group[it] = mutableSetOf()
    }

    edges.forEach { edge ->
        group[edge.first()]!!.add(edge.last())
        group[edge.last()]!!.add(edge.first())
    }

    visited.add(0)
    recursive(0, group, restricted)

    return visited.count()
}

private fun recursive(
    startNodeNumber: Int,
    group: Map<Int,MutableSet<Int>>,
    restricted: IntArray
) {

    // 해당노드는 제한된 노드
    val toBeNodes = group[startNodeNumber]!!

    val filteredToBeNodes = toBeNodes.filter { node ->
        visited.contains(node).not()
    }.filter { node ->
        restricted.contains(node).not()
    }

    filteredToBeNodes.forEach { node ->
        // node 는 방문한다.
        visited.add(node)
        recursive(node, group, restricted)
    }

    return
}