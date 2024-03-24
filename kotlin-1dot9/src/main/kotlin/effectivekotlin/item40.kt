package effectivekotlin

import java.net.URI
import java.net.URL

class item40

fun main() {
    val wiki1 = URL("https://en.wikipedia.org/")
    val wiki2 = URL("https://wikipedia.org/")

    val uri1 = URI("https://en.wikipedia.org/")
    val uri2 = URI("https://wikipedia.org/")

    println(wiki1 == wiki2)
    println(uri1 == uri2)
}