package leetcode.weekly288

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.min

class `2232` {

    @Test
    fun leetcodeTest() {
        minimizeResult("247+38") shouldBe "2(47+38)"
        minimizeResult("12+34") shouldBe "1(2+3)4"
        minimizeResult("999+999") shouldBe "(999+999)"
    }
}

fun minimizeResult(expression: String): String {
    val spliter = expression.split("+")

    val leftList = createLeftParenthesis(spliter[0], "(")
    val rightList = createLeftParenthesis(spliter[1], ")")

    val set = mutableSetOf<String>()
    leftList.forEach { leftElement ->
        rightList.forEach { rightElement ->
            set.add("$leftElement+$rightElement")
        }
    }

    val pattern = Regex("\\([0-9]+\\+[0-9]+\\)")
    var result = Integer.MAX_VALUE
    val map = mutableMapOf<Int, String>()

    set.forEach { arithmetic ->
        val matchResult = pattern.find(arithmetic)!!
        val start = matchResult.range.first
        val end = matchResult.range.last

        val twoNumber = arithmetic.substring(start + 1, end).split("+")
        val sum = twoNumber.first().toInt() + twoNumber.last().toInt()
        val startMultiply = if (arithmetic.substring(0, start).isEmpty()) 1 else arithmetic.substring(0, start).toInt()
        val endMultiply = if (arithmetic.substring(end + 1, arithmetic.count()).isEmpty()) 1 else arithmetic.substring(end + 1, arithmetic.count()).toInt()

        result = min(result, startMultiply * sum * endMultiply)
//        println("$twoNumber, $arithmetic, $sum, $result")
        map[startMultiply * sum * endMultiply] = "${if(startMultiply == 1 && arithmetic.first() != '1') "" else startMultiply}${arithmetic.substring(start, end + 1)}${if (endMultiply == 1 && arithmetic.last() != '1') "" else endMultiply}"
    }

//    println(map.toString())

    return map[result]!!
}

private fun createLeftParenthesis(value: String, parenthesis: String): List<String> {
    return if (parenthesis == "(") {
        value.mapIndexed { index, char ->
            value.substring(0, index) + parenthesis + value.substring(index, value.count())
        }
    } else {
        val result = value.mapIndexed { index, char ->
            value.substring(0, index) + parenthesis + value.substring(index, value.count())
        }.toMutableList()
        result.removeAt(0)
        result.add("$value)")
        result.toList()
    }
}