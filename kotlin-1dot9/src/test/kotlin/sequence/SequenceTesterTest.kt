package sequence

import org.junit.jupiter.api.RepeatedTest
import kotlin.system.measureTimeMillis

class SequenceTesterTest {

    @RepeatedTest(5)
    fun `operation count 에 따른 sequence vs collection 비교`() {
        val coffees = (1..10000000).map { Coffee("old-coffee-$it", it) }

        val elapsedTimeWithCollection = measureTimeMillis {
            val one = coffees
                .filter { it.sequence % 3 == 0 }
                .map { it.copy("new-coffee-${it.sequence}", it.sequence) }
                .take(1)
                .toSet()

            println(one)
        }
        println("use collection, elapsedTime= ${elapsedTimeWithCollection} milliSec")

        val elapsedTimeWithSequence = measureTimeMillis {
            val one = coffees
                .asSequence() // asSequence() 처리.
                .filter { it.sequence % 3 == 0 }
                .map { it.copy("new-coffee-${it.sequence}", it.sequence) }
                .take(1)
                .toSet()

            println(one)
        }
        println("use sequence, elapsedTime= ${elapsedTimeWithSequence} milliSec")

        println("\n============================================================\n")
    }

    @RepeatedTest(5)
    fun `더이상 반복할 필요가 없는 것들 sequence vs collection 비교`() {
        val coffees = (1..10000000).map { Coffee("old-coffee-$it", it) }

        val elapsedTimeWithCollection = measureTimeMillis {
            val one = coffees
                .filter { it.sequence % 3 == 0 }
                .map { it.copy("new-coffee-${it.sequence}", it.sequence) }
                .any() // any

            println(one)
        }
        println("use collection, elapsedTime= ${elapsedTimeWithCollection} milliSec")

        val elapsedTimeWithSequence = measureTimeMillis {
            val one = coffees
                .asSequence() // asSequence() 처리.
                .filter { it.sequence % 3 == 0 }
                .map { it.copy("new-coffee-${it.sequence}", it.sequence) }
                .any() // any

            println(one)
        }
        println("use sequence, elapsedTime= ${elapsedTimeWithSequence} milliSec")

        println("\n============================================================\n")
    }
}

data class Coffee(
    val name: String,
    val sequence: Int
)