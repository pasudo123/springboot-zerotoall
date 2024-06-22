import kotlin.test.Test

class LeetCodeLauncher {

    @Test
    fun `leetcode 풀이`() {
        val solution = Solution()
        println(solution.findWords(arrayOf("Hello", "Alaska", "Dad", "Peace")).toList())
    }

}

class Solution {
    fun findWords(words: Array<String>): Array<String> {
        val firstRows: Set<Char> = setOf('q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p')
        val secondRows: Set<Char> = setOf('a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l')
        val thirdRows: Set<Char> = setOf('z', 'x', 'c', 'v', 'b', 'n', 'm')

        return words.filter { word ->
            val charGroup = word.toCharArray()

            val firstCheck = charGroup.all { current ->
                firstRows.contains(current.lowercaseChar())
            }
            if (firstCheck) {
                return@filter true
            }

            val secondCheck = charGroup.all { current ->
                secondRows.contains(current.lowercaseChar())
            }
            if (secondCheck) {
                return@filter true
            }

            val thirdCheck = charGroup.all { current ->
                thirdRows.contains(current.lowercaseChar())
            }
            if (thirdCheck) {
                return@filter true
            }

            false
        }.toTypedArray()
    }
}