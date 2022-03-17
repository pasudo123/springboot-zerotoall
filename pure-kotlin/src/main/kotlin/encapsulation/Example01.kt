package encapsulation

import kotlin.random.Random

class Example01

/**
 * backing property 와 backing field 를 이용한 캡슐화
 */
class GameScore {
    private var _score = mutableListOf<Long>()

    val score: List<Long>
        get() = _score.toList()

    fun addElementScore() {
        _score.add(random)
    }

    fun removeLastElementScore() {
        _score.removeLast()
    }

    private val random
        get(): Long { return Random.nextLong(10, 99) }
}

fun main() {

    val gameScore = GameScore()
    println(gameScore.score)

    gameScore.addElementScore()
    println(gameScore.score)

    gameScore.addElementScore()
    println(gameScore.score)

    gameScore.removeLastElementScore()
    println(gameScore.score)
}