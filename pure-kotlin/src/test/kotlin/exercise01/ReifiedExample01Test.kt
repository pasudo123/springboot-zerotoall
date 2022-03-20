package exercise01

import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import model.Coffee
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reified.toJson
import reified.toObjectVersion1
import reified.toObjectVersion2

class ReifiedExample01Test {

    @Test
    @DisplayName("toJson() 을 수행한다.")
    fun toJsonTest() {

        // given
        val coffee = Coffee("아메리카노", 3000)

        // when
        val actual = coffee.toJson()

        // then
        actual shouldNotBe null
    }

    @Test
    @DisplayName("toObjectVersion1() 를 수행한다.")
    fun toObjectVersion1Test() {

        // given
        val coffee = Coffee("카페라떼", 4500)
        val json = coffee.toJson()

        // when
        val actual = json.toObjectVersion1(Coffee::class.java)

        // then
        actual.asClue {
            it.id shouldNotBe null
            it.name shouldBe "카페라떼"
            it.price shouldBe 4500
        }
    }

    @Test
    @DisplayName("toObjectVersion2() 를 수행한다.")
    fun toObjectVersion2Test() {

        // given
        val coffee = Coffee("카페라떼", 4500)
        val json = coffee.toJson()

        // when
        val actual = json.toObjectVersion2<Coffee>()

        // then
        actual.asClue {
            it.id shouldNotBe null
            it.name shouldBe "카페라떼"
            it.price shouldBe 4500
        }
    }
}