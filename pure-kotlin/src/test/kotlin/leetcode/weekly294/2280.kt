package leetcode.weekly294

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

@Deprecated("풀고 있는 중... 기울기 비율이 계산이 안됨..")
class `2280` {

    @Test
    fun leetcodeTest() {
//        minimumLines(arrayOf(
//            intArrayOf(1, 7), intArrayOf(2, 6), intArrayOf(3, 5), intArrayOf(4, 4), intArrayOf(5, 4),
//            intArrayOf(6, 3), intArrayOf(7, 2), intArrayOf(8, 1)
//        )) shouldBe 3
//
//        minimumLines(arrayOf(
//            intArrayOf(3, 4), intArrayOf(1, 2), intArrayOf(7, 8), intArrayOf(2, 3)
//        )) shouldBe 1
//
//        minimumLines(arrayOf(
//            intArrayOf(1, 1)
//        )) shouldBe 0

        minimumLines(arrayOf(
            intArrayOf(1, 1), intArrayOf(500000000,499999999), intArrayOf(1000000000,999999998)
        )) shouldBe 2
    }
}

fun minimumLines(stockPrices: Array<IntArray>): Int {

    if (stockPrices.size == 1) {
        return 0
    }

    // 정렬
    val sortedStockPrices = stockPrices.sortedBy { stockPrice ->
        stockPrice.first()
    }

    var lineCount = 1

    // 첫번째와 두번째 비율을 선 계산
    var xInc: Long = (sortedStockPrices[1].first() - sortedStockPrices[0].first()).toLong()
    var yInc: Long = (sortedStockPrices[1].last() - sortedStockPrices[0].last()).toLong()

    for (index in 1 until sortedStockPrices.size) {
        val currentStockPrice = sortedStockPrices[index]

        if (index < sortedStockPrices.size - 1) {
            val nextStockPrice = sortedStockPrices[index + 1]
            val subXInc = (nextStockPrice.first() - currentStockPrice.first()).toLong()
            val subYInc = (nextStockPrice.last() - currentStockPrice.last()).toLong()

            if (xInc.div(yInc) == subXInc.div(subYInc)) {
                continue
            }

            lineCount++
            xInc = subXInc
            yInc = subYInc
        }
    }

    return lineCount
}