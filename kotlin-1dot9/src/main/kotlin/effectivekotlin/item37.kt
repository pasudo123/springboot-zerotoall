package effectivekotlin

class item37

data class Player(
    val name: String,
    val age: Int,
    val gender: Gender
) {
    enum class Gender {
        M, W
    }
}

fun main() {
    val player1 = Player("park", 10, Player.Gender.M)
    val player2 = Player("park", 10, Player.Gender.M)
    val player1Reference = player1

    // 구조적 동등성 : ==
    println("구조적 동등성")
    println(player1 == player2) // true
    println(player1 == player1Reference) // true

    // 레퍼런스적 동등성 : ===
    println("레퍼런스적 동등성")
    println(player1 === player2) // false
    println(player1 === player1Reference) // true

    val player3 = player1.copy(name = "kim")
    println("테스트")
    println(player1 == player3) // false
    println(player1 === player3) // false
}