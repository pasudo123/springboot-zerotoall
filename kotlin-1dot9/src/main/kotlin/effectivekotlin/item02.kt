package effectivekotlin

class item02

fun main() {
    sequenceV1()
    println()
    sequenceV2()
}

private fun sequenceV1() {
    val primes: Sequence<Int> = sequence {
        var numbers = generateSequence(2) { it + 1 }

        while(true) {
            val prime = numbers.first()
            yield(prime)
            numbers = numbers
                .drop(1)
                .filter { it % prime != 0 }
        }
    }

    print("v1 : ${primes.take(10).toList()}")
}

@Suppress("DuplicatedCode")
private fun sequenceV2() {
    val primes: Sequence<Int> = sequence {
        var numbers = generateSequence(2) { it + 1 }

        var prime: Int
        while(true) {
            prime = numbers.first()
            println("prime=$prime")
            yield(prime)
            numbers = numbers
                .drop(1)
                .filter { it % prime != 0 }
        }
    }
    print("v2 : ${primes.take(10).toList()}")
}