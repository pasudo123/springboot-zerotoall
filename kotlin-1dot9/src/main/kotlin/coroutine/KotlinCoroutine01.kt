package coroutine

import kotlinx.coroutines.delay

suspend fun main1() {
    println("Hello")
    delay(500L)
    println("Kotlin")
    delay(500L)
    println("Coroutine")
    delay(500L)
    println("World")
}