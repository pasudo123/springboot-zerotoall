package leetcode.weekly293

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2273` {

    @Test
    fun leetcodeTest() {
        removeAnagrams(arrayOf("abba","baba","bbaa","cd","cd")) shouldBe listOf("abba","cd")
        removeAnagrams(arrayOf("a","b","c","d","e")) shouldBe listOf("a","b","c","d","e")
        removeAnagrams(arrayOf("a","b","a")) shouldBe listOf("a","b","a")
    }
}

fun removeAnagrams(words: Array<String>): List<String> {

    val group = mutableListOf<MutableMap<Char, Int>>()
    val result = mutableListOf<String>()

    for (index in words.indices) {
        val word = words[index]
        val currentGroup = mutableMapOf<Char, Int>()

        word.forEach { element ->
            if (currentGroup[element] == null) {
                currentGroup[element] = 1
            } else {
                currentGroup[element] = currentGroup[element]!! + 1
            }
        }

        if (index == 0) {
            group.add(currentGroup)
            result.add(word)
            continue
        }

        var groupMatched = false

        group.forEach { subGroup ->
            var isMatched = true

            subGroup.keys.forEach { key ->
                if (subGroup[key] == currentGroup[key]) {
                    isMatched = isMatched && true
                } else {
                    isMatched = false
                }
            }

            if (isMatched) {
                groupMatched = true
            }
        }

        if (groupMatched.not()) {
            group.add(currentGroup)
            result.add(word)
        }
    }

    return result
}