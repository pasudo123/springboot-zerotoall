package exercise01

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class ReifiedExample02Test {

    @Test
    @DisplayName("")
    fun printTypeWithGenericAdvancedTest() {

        // given
        val param = "apple"

        // when & then
        param.printTypeWithGenericAdvanced(String::class.java)
    }

    @Test
    @DisplayName("")
    fun printTypeWithReifiedTest() {

        // given
        val param = 1L

        // when & then
        param.printTypeWithReified()
    }
}