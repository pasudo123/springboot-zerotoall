package leetcode.weekly289

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

@Deprecated("어려움")
class `2244` {

    @Test
    fun leetcodeTest() {
//        minimumRounds(intArrayOf(2,2,2, 3,3, 4,4,4 ,4,4)) shouldBe 4
//        minimumRounds(intArrayOf(2,3,3)) shouldBe -1
        minimumRounds(intArrayOf(5,5,5,5)) shouldBe 2
    }
}

fun minimumRounds(tasks: IntArray): Int {
    val sortedTasks = tasks.sortedArray()
    return recursiveRounds(0, sortedTasks, 0)
}

fun recursiveRounds(currentIndex: Int, tasks: IntArray, totalRounds: Int): Int {

    println("curr $currentIndex")

    if (currentIndex >= tasks.size) {
        return totalRounds
    }

    if (currentIndex + 3 <= tasks.size) {
        if (tasks[currentIndex] == tasks[currentIndex + 1]
            && tasks[currentIndex + 1] == tasks[currentIndex + 2]) {
            return recursiveRounds(currentIndex + 3, tasks, totalRounds + 1)
        }
    }

    if (currentIndex + 2 <= tasks.size) {
        if (tasks[currentIndex] == tasks[currentIndex + 1]) {
            return recursiveRounds(currentIndex + 2, tasks, totalRounds + 1)
        }
    }

    return totalRounds - (totalRounds + 1)
}