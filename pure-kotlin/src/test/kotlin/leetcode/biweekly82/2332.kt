package leetcode.biweekly82

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@Deprecated("문제 어렵다. 안풀림")
class `2332` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {
        latestTimeCatchTheBus(intArrayOf(2), intArrayOf(2), 2) shouldBe 2
        latestTimeCatchTheBus(intArrayOf(3), intArrayOf(2, 4), 2) shouldBe 3
        latestTimeCatchTheBus(intArrayOf(10, 20), intArrayOf(2,17,18,19), 2) shouldBe 16
        latestTimeCatchTheBus(intArrayOf(10, 20, 30), intArrayOf(19,13,26,4,25,11,21), 2) shouldBe 20
    }
}

fun latestTimeCatchTheBus(buses: IntArray, passengers: IntArray, capacity: Int): Int {

    val sortedBuses = buses.sorted()
    val sortedPassengers = passengers.sorted()
    val rideGroup = mutableListOf<MutableList<Int>>()
    var passengerIndex = 0


    for (index in sortedBuses.indices) {
        val busArrivalTime = sortedBuses[index]
        var currentCapacity = capacity
        val subList = mutableListOf<Int>()

        while (passengerIndex < passengers.size) {
            val passengerArrivalTime = sortedPassengers[passengerIndex]

            if (passengerArrivalTime <= busArrivalTime) {
                subList.add(passengerArrivalTime)
                currentCapacity--
            } else {
                break
            }

            if (currentCapacity == 0) {
                passengerIndex++
                break
            }

            passengerIndex++
        }

        rideGroup.add(subList)
    }

    val lastGroup = rideGroup.last()
    val reversedLastGroup = lastGroup.reversed()
    reversedLastGroup.forEachIndexed { index, element ->
        if (index >= 1 && reversedLastGroup[index - 1] - 1 != element) {
            return reversedLastGroup[index - 1] - 1
        }
    }

    if (rideGroup.last().size != capacity) {
        return rideGroup.last().last() + 1
    }

    return rideGroup.last().first() - 1
}