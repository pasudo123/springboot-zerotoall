package leetcode.weekly287

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class `2224` {

    @Test
    fun leetcodeTest() {
//        convertTime("02:30", "04:35") shouldBe 3
//        convertTime("11:00", "11:01") shouldBe 1
        convertTime("09:41", "10:34") shouldBe 7
    }
}

fun convertTime(current: String, correct: String): Int {
    var currentHour = current.split(":").first().toInt()
    var currentMinute = current.split(":").last().toInt()

    var correctHour = correct.split(":").first().toInt()
    var correctMinute = correct.split(":").last().toInt()

    if (currentMinute > correctMinute) {
        correctHour =- 1
        correctMinute += 60
    }

    val diffHour = if (correctHour - currentHour < 0) 0 else correctHour - currentHour
    var diffMinute = correctMinute - currentMinute

    var count = 0

    count += diffHour
    var share = diffMinute / 15
    if (share != 0) {
        count += share
    }

    diffMinute %= 15
    share = diffMinute / 5
    if (share != 0) {
        count += share
    }

    diffMinute %= 5
    share = diffMinute / 1
    if (share != 0) {
        count += share
    }

    return count
}