package toby.chapter01

import java.util.Observer
import java.util.concurrent.Executors

class Chapter01

/**
 * java 의 for-each 는 collection 이 아니라 iterable 을 넣는다.
 */
fun main() {

    /**
     * iterable 내에서 iterator 를 반복적으로 사용할 수 있다. (반복사용가능)
     */

    /**
     * Duality : Iterable[Pull] <--> Observable[Push]
     */

    // pullMethod()

    val exec = Executors.newSingleThreadExecutor()
    exec.execute {
        Thread.sleep(1000L)
        println("[${Thread.currentThread().name}] Exec pushMethod()")
        pushMethod()
    }
    exec.shutdown()
    println("[${Thread.currentThread().name}] Close")
}

/**
 * push 방식으로 옵저버패턴을 이용하면 별도의 스레드에서 실행시킬 수 있음
 */
@Suppress("DEPRECATION")
private fun pushMethod() {
    val observer = Observer { observable, arg -> println("[${Thread.currentThread().name}] args : $arg") }

    IntObservable.addObserver(observer)
    IntObservable.run()
}

private fun pullMethod() {
    val iter = Iterable(object : Iterator<Int>, () -> Iterator<Int> {
        private var i = 0
        private val MAX = 10

        override fun hasNext(): Boolean {
            return i < MAX
        }

        // pull
        override fun next(): Int {
            return ++i
        }

        override fun invoke(): Iterator<Int> {
            return this
        }
    })

    for (element in iter) {
        println(element)
    }
}
