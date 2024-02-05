package chapter01

import kotlin.concurrent.thread

class item01

fun main() {
    var num = 0;

    for ( i in 1..100) {
        thread {
            Thread.sleep(10)
            num += 1
        }
    }

    Thread.sleep(5000)
    println(num)
}