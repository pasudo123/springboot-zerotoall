package coroutine

import coroutine.Flow05

fun main() {

//    val flow02 = Flow02()
//    flow02.main()

//    val flow03 = Flow03()
//    flow03.main()

//    val flow04 = Flow04()
//    flow04.main()

//    val flow05 = Flow05()
//    flow05.flowBuilder01()
//    flow05.flowBuilder02()
}

fun printlnWithThreadName(message: String) {
    println("[${Thread.currentThread().name}] : $message")
}