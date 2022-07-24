package leetcode.weekly300

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2325` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {
        decodeMessage("the quick brown fox jumps over the lazy dog", "vkbs bs t suepuv") shouldBe "this is a secret"
        decodeMessage("eljuxhpwnyrdgtqkviszcfmabo", "zwx hnfx lqantp mnoeius ycgk vcnjrdb") shouldBe "the five boxing wizards jump quickly"
    }
}

fun decodeMessage(key: String, message: String): String {
    val group = mutableMapOf<Char, Char>()
    var eng = 'a'

    key.forEach {
        if (it == ' ' || group.containsKey(it)) {
            return@forEach
        }

        group[it] = eng
        eng++
    }

    var result = ""
    message.forEach {
        if (group.containsKey(it).not()) {
            result += " "
        } else {
            result += group[it]
        }
    }

    return result
}