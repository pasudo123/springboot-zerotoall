package keep

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KParameter

class ContinuationDemo

suspend fun main() {
    wakeup()
    washMyFace()
    wearClothes()
    workToOffice()
}

suspend fun wakeup() = coroutineScope {
    println("일어나다.")
}

suspend fun washMyFace() = coroutineScope {
    println("세수를 하다.")
}

suspend fun wearClothes() = coroutineScope {
    println("옷을 입다.")
}

suspend fun workToOffice() = coroutineScope{
    println("일하러가다.")
}