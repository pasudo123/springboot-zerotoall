package morak

import java.util.Base64

class MorakBase64

fun main() {
//    val origin = "Many hands make light work."
    val origin = "M"
    val originByteArray = origin.toByteArray()
    val asciiNumbers = originByteArray.map { byte -> byte.toUInt() }

    println("origin=$origin")
    println("origin-byte-array=${asciiNumbers}")
    println("binary-data=${asciiNumbers.map { it.toString(2) }}")

    val encodeString = Base64.getEncoder().encodeToString(originByteArray)
    println("encode=$encodeString")
    val decodeString = Base64.getDecoder().decode(encodeString).decodeToString()
    println("decode=$decodeString")
}
