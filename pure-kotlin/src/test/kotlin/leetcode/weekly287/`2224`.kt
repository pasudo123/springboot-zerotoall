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
    val currentHour = current.split(":").first().toInt()
    val currentMinute = current.split(":").last().toInt()
    val correctHour = correct.split(":").first().toInt()
    val correctMinute = correct.split(":").last().toInt()

    if (currentHour == correctHour) {
        return if (currentMinute == correctMinute) {
            0
        } else if (currentMinute > correctMinute) {
            // 하루
            23 + getCountByMinute(60 - currentMinute + correctMinute)
        } else {
            getCountByMinute(correctMinute - currentMinute)
        }
    }

    if (currentHour < correctHour) {
        return if (currentMinute == correctMinute) {
            correctHour - currentHour
        } else if (currentMinute > correctMinute) {
            (correctHour - currentHour - 1) + getCountByMinute(60 - currentMinute + correctMinute)
        } else {
            (correctHour - currentHour) + getCountByMinute(correctMinute - currentMinute)
        }
    }

    return if (currentMinute == correctMinute) {
        (24 - currentHour) + correctHour
    } else if (currentMinute > correctMinute) {
        (23 - currentHour + getCountByMinute(60 - currentMinute + correctMinute) - 1) + correctHour
    } else {
        (23 - currentHour + getCountByMinute(60 - currentMinute + correctMinute) - 1) + correctHour
    }
}

private fun getCountByMinute(paramDiffMinute: Int): Int {
    var diffMinute = paramDiffMinute
    var count = 0
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