package coroutine

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

class Flow05 {

    fun flowBuilder01() = runBlocking {
        (1..10)
            .asFlow()
            .collect { value -> printlnWithThreadName("[1] current value :: $value") }
    }

    fun flowBuilder02() = runBlocking {
        flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .collect { value -> printlnWithThreadName("[2] current value :: $value") }
    }
}

fun main() {
    val flows05 = Flow05().run {
        this.flowBuilder01()
        this.flowBuilder02()
    }
}