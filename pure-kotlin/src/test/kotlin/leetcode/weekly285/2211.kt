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

        countCollisions("RLRSLL") shouldBe 5
        countCollisions("LLRR") shouldBe 0
        countCollisions("SSRSSRLLRSLLRSRSSRLRRRRLLRRLSSRR") shouldBe 20

        /**
         * SSRSSRLLRSLLRSRSSRLRRRRLLRRLSSRR
         * SSSSSSSRRRRLLRRLSSRR
         */
    }
}

fun countCollisions(directions: String): Int {

    if (directions.count() <= 1) {
        return 0
    }

    var count = 0
    var newDirections = directions.first().toString()
    var colison = false
    var index = 1

    while (index < directions.count()) {
        val currentDirection = directions[index]

        if (newDirections.last() == 'R' && currentDirection == 'L') {
            newDirections = "${newDirections}S"
            index += 2
            count += 2
            count += doRecursive(newDirections.substring(0, newDirections.length - 1), 0)
            continue
        }

        if (newDirections.last() == 'S' && currentDirection == 'L') {
            newDirections = "${newDirections}S"
            index += 1
            count += 1
            count += doRecursive(newDirections.substring(0, newDirections.length - 1), 0)
            continue
        }

        if (newDirections.last() == 'R' && currentDirection == 'S') {
            newDirections = "${newDirections}S"
            index += 1
            count += 1
            count += doRecursive(newDirections.substring(0, newDirections.length - 1), 0)
            continue
        }

        newDirections = "$newDirections$currentDirection"
        index += 1
    }

    return count
}

private fun doRecursive(newDirections: String, count: Int): Int {

    if (newDirections.isEmpty()) {
        return count
    }

    if (newDirections.last() == 'R') {
        return doRecursive(newDirections.substring(0, newDirections.length - 1), count + 1)
    }

    return count
}
