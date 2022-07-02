package leetcode.weekly282

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2185` {

    @Test
    fun leetcodeTest() {
        prefixCount(arrayOf("pay","attention","practice","attend"), "at") shouldBe 2
        prefixCount(arrayOf("leetcode","win","loops","success"), "code") shouldBe  0
    }
}

fun prefixCount(words: Array<String>, pref: String): Int {
    var count = 0

    words.forEach { word ->
        if (word.startsWith(pref)) {
            count++
        }
    }

    return count
}