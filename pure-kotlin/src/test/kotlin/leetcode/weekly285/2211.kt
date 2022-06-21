package leetcode.weekly285

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

/**
 * https://leetcode.com/contest/weekly-contest-285/problems/count-collisions-on-a-road/
 * 다시풀기
 */
class `2211` {

    @Test
    fun leetcodeTest() {

//        countCollisions("RLRSLL") shouldBe 5
//        countCollisions("LLRR") shouldBe 0
        countCollisions("SSRSSRLLRSLLRSRSSRLRRRRLLRRLSSRR") shouldBe 20

        /**
         * SSRSSRLLRSLLRSRSSRLRRRRLLRRLSSRR
         * SSSSSSSRRRRLLRRLSSRR
         */
    }
}

fun countCollisions(directions: String): Int {
    return recursive(directions, 0)
}

private fun recursive(directions: String, sum: Int): Int {

    if (directions.count() <= 1) {
        return sum
    }

    var count = 0
    var lastDirection = directions.first()
    var newDirections = ""

    for (index in 1 until directions.count()) {
        val currentDirection = directions[index]

        if (lastDirection == 'R' && currentDirection == 'L') {
            count += 2
            lastDirection = 'S'
            newDirections = if (newDirections.isNotEmpty() && newDirections.last() == 'S') newDirections else "$newDirections$lastDirection"
            continue
        }

        if (lastDirection == 'S' && currentDirection == 'L') {
            count += 1
            lastDirection = 'S'
            newDirections = if (newDirections.isNotEmpty() && newDirections.last() == 'S') newDirections else "$newDirections$lastDirection"
            continue
        }

        if (lastDirection == 'R' && currentDirection == 'S') {
            count += 1
            lastDirection = 'S'
            newDirections = if (newDirections.isNotEmpty() && newDirections.last() == 'S') newDirections else "$newDirections$lastDirection"
            continue
        }

        lastDirection = currentDirection
        // newDirections = if (newDirections.isNotEmpty() && newDirections.last() == 'S') newDirections else "$newDirections$lastDirection"
    }

    return recursive(newDirections, count)
}