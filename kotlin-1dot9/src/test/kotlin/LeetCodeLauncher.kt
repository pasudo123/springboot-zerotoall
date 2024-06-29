import kotlin.test.Test

class LeetCodeLauncher {

    @Test
    fun `leetcode 풀이`() {
        Solution().run { println(numTilePossibilities("V")) }
    }
}

class Solution {

    companion object {
        val result = mutableSetOf<String>()
    }

    fun numTilePossibilities(tiles: String): Int {
        result.clear()

        val groups = mutableMapOf<Char, Int>()

        tiles.forEach { tile ->
            groups.computeIfPresent(tile) { _, value -> value + 1 }
            groups.putIfAbsent(tile, 1)
        }

        groups.keys.forEach { key ->
            dfs(possibleTile = "$key", key, groups.toMutableMap())
        }

        // remove empty
        return result.count()
    }

    private fun dfs(possibleTile: String, currentTile: Char, groups: MutableMap<Char, Int>) {
        val currentCount =  groups[currentTile]!! - 1

        if (currentCount == 0) {
            groups.remove(currentTile)
        } else {
            groups[currentTile] = currentCount
        }

        result.add(possibleTile)
        println(possibleTile)

        if (groups.values.isEmpty()) {
            return
        }

        groups.keys.forEach { key ->
            dfs(possibleTile = "$possibleTile$key", key, groups)
        }
    }
}