package sequence

import kotlin.random.Random
import kotlin.test.Test

class SequenceRandomTest {

    @Test
    fun `yield 를 사용한다`() {
        val randomSequence = sequence {
            while (true) {
                val number = Random.nextLong(0, 10001)
                println("hello-$number")
                yield(number)
            }
        }

        repeat(100) {
            val it = randomSequence.iterator()
            println(it.next())
        }
    }
}