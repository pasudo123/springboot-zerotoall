package leetcode.weekly293

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2273` {

    @Test
    fun leetcodeTest() {
//        removeAnagrams(arrayOf("abba","baba","bbaa","cd","cd")) shouldBe listOf("abba","cd")
//        removeAnagrams(arrayOf("a","b","c","d","e")) shouldBe listOf("a","b","c","d","e")
//        removeAnagrams(arrayOf("a","b","a")) shouldBe listOf("a","b","a")
        removeAnagrams(arrayOf("z","z","z","gsw","wsg","gsw","krptu")) shouldBe listOf("z","gsw","krptu")
    }
}

fun removeAnagrams(words: Array<String>): List<String> {

    if (words.count() == 1) {
        return words.toList()
    }

    var prevGroup = mutableMapOf<Char, Int>()

    words.first().forEach { element ->
        if (prevGroup[element] == null) {
            prevGroup[element] = 1
        } else {
            prevGroup[element] = prevGroup[element]!! + 1
        }
    }

    val result = mutableListOf<String>()
    result.add(words.first())

    for (index in 1 until words.size) {
        val word = words[index]
        val currentGroup = mutableMapOf<Char, Int>()

        word.forEach { element ->
            if (currentGroup[element] == null) {
                currentGroup[element] = 1
            } else {
                currentGroup[element] = currentGroup[element]!! + 1
            }
        }

        if (prevGroup.keys.size != currentGroup.keys.size) {
            prevGroup = currentGroup
            result.add(word)
            continue
        }

        var isAnagram = true

        prevGroup.keys.forEach { key ->
            if (prevGroup[key] == currentGroup[key]) {

            } else {
                isAnagram = false
            }
        }

        if (isAnagram) {
            continue
        }

        result.add(word)
        prevGroup = currentGroup
    }

    return result
}