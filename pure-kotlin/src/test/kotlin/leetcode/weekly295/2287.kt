package leetcode.weekly295

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.min

class `2287` {

    @Test
    fun leetcodeTest() {
        rearrangeCharacters("abbaccaddaeea", "aaaaa") shouldBe 1
        rearrangeCharacters("abcba", "abc") shouldBe 1
        rearrangeCharacters("ilovecodingonleetcode", "code") shouldBe 2
    }
}

fun rearrangeCharacters(s: String, target: String): Int {

    val toBeCountGroup = mutableMapOf<Char, Int>()
    val countGroup = mutableMapOf<Char, Int>()

    target.forEach { ch ->
        if (countGroup.containsKey(ch).not()) {
            countGroup[ch] = 1
        } else {
            countGroup[ch] = countGroup[ch]!! + 1
        }

        toBeCountGroup[ch] = 0
    }

    s.forEach { ch ->
        if (countGroup.containsKey(ch)) {
            toBeCountGroup[ch] = toBeCountGroup[ch]!! + 1
        }
    }

    var result = Integer.MAX_VALUE
    toBeCountGroup.keys.forEach { key ->
        result = min(result, toBeCountGroup[key]!!.div(countGroup[key]!!))
    }

    return result
}