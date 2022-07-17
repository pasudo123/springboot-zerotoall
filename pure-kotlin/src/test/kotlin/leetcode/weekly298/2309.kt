package leetcode.weekly298

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2309` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {
        greatestLetter("lEeTcOdE") shouldBe "E"
        greatestLetter("arRAzFif") shouldBe "R"
        greatestLetter("AbCdEfGhIjK") shouldBe ""
        greatestLetter("nzmguNAEtJHkQaWDVSKxRCUivXpGLBcsjeobYPFwTZqrhlyOIfdM") shouldBe "Z"
    }
}

fun greatestLetter(s: String): String {

    val group = mutableSetOf<Char>()
    ('A'..'Z').forEach {
        group.add(it)
    }

    ('a'..'z').forEach {
        group.add(it)
    }

    val charGroup = mutableListOf<Char>()

    s.forEach {
        if (group.contains(it) && it.isUpperCase()) {
            group.remove(it)
            if (group.contains(it.lowercaseChar()).not()) {
                charGroup.add(it.lowercaseChar())
            }
        }

        if (group.contains(it) && it.isLowerCase()) {
            group.remove(it)
            if (group.contains(it.uppercaseChar()).not()) {
                charGroup.add(it.lowercaseChar())
            }
        }
    }

    if (charGroup.isEmpty()) {
        return ""
    }

    return charGroup.sorted().last().uppercase()
}

fun Char.lowercaseChar(): Char = Character.toLowerCase(this)
fun Char.uppercaseChar(): Char = Character.toUpperCase(this)
fun Char.uppercase(): String = this.toString().uppercase()