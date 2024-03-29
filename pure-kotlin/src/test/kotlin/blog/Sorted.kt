package blog

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Sorted {

    @Test
    fun sortedTest() {

        val colorGroup = mutableMapOf<String, Int>().apply {
            this["red"] = 555
            this["blue"] = 77
            this["yellow"] = 23
            this["gray"] = 356
            this["cyan"] = 555
        }

        // Int 기준으로 오름차순
        val result1 = colorGroup.toList().sortedWith(compareByDescending { it.second }).map { it.first }
        result1 shouldBe listOf("red", "cyan", "gray", "blue", "yellow")

        // value 기준으로 내림차순, 동일 value 면 key 값 기준으로 오름차순 (알파벳)
        val result2 = colorGroup.toList().sortedWith(compareByDescending<Pair<String, Int>> { it.second }.thenBy { it.first }).map { it.first }
        result2 shouldBe listOf("cyan", "red", "gray", "blue", "yellow")
    }

    @Test
    fun classSortedTest() {

        val colorGroup = listOf<MyColor>(
            MyColor("red", 555),
            MyColor("blue", 77),
            MyColor("yellow", 23),
            MyColor("gray", 356),
            MyColor("cyan", 555)
        )

        // count 기준으로 내림차순
        val sortedByCount = colorGroup.sortedWith(compareByDescending { it.count }).map { it.name }
        sortedByCount shouldBe listOf("red", "cyan", "gray", "blue", "yellow")

        val sortedByCountAndColor = colorGroup.sortedWith(compareByDescending<MyColor> { it.count }.thenBy { it.name }).map { it.name }
        sortedByCountAndColor shouldBe listOf("cyan", "red", "gray", "blue", "yellow")
    }
}

data class MyColor(
    val name: String,
    val count: Int
)