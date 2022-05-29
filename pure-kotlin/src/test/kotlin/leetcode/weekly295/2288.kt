package leetcode.weekly295

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class `2288` {

    @Test
    fun leetcodeTest() {
        println((3 - (3 * (100 / 100))).toBigDecimal().setScale(2))
        println(((1 - (1 * (50 / 100.00))) * 100.00).div(100).toBigDecimal().setScale(2))
        println(602.toBigDecimal().subtract((602 * (9 / 100.00)).toBigDecimal().setScale(2)))
        println((602 * (9 / 100.00)).toBigDecimal().setScale(2))

        discountPrices("1 2 $3 4 $5 $6 7 8$ $9 $10$", 100) shouldBe
                "1 2 $0.00 4 $0.00 $0.00 7 8$ $0.00 $10$"

        discountPrices("there are $1 $2 and 5$ candies in the shop", 50) shouldBe
                "there are $0.50 $1.00 and 5$ candies in the shop"

        discountPrices("$19 q1zi9oqt m", 0) shouldBe
                "$19.00 q1zi9oqt m"
    }
}

val dollarRegex: Regex = Regex("(^\\\$[0-9]+\$)")

fun discountPrices(sentence: String, discount: Int): String {
    val elements = sentence.split(" ")
    val result = mutableListOf<String>()

    elements.forEach { element ->
        val matchResult = dollarRegex.matchEntire(element)

        if (matchResult == null) {
            result.add(element)
            return@forEach
        }

        if (discount == 100) {
            result.add("$0.00")
            return@forEach
        }

        val dollar = matchResult.value.substring(1).toLong()
        val discountDollar = dollar.toBigDecimal().multiply(discount.toBigDecimal().divide(BigDecimal.valueOf(100.0)))
        val discountDollarResult = dollar.toBigDecimal().subtract(discountDollar).setScale(2)
        result.add("$${discountDollarResult.toPlainString()}")
    }

    return result.joinToString(" ")
}