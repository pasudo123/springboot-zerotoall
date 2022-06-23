package blog

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class Nullable {

    @Test
    @DisplayName("nullable 을 잘쓰자")
    fun nullableToStringTest() {
        getPersonAge(Person(10)) shouldBe "10"
        getPersonAge(Person()) shouldBe "empty age"
    }

    private fun getPersonAge(person: Person? = null): String {
        return person?.age?.toString() ?: "empty age"
    }
}

data class Person(
    val age: Int? = null
)