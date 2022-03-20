package exercise01

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reified.printTypeWithGenericAdvanced
import reified.printTypeWithReified

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
        val paramString = "Hello"
        val paramInteger = 5

        // when & then
        param.printTypeWithReified()
        paramString.printTypeWithReified()
        paramInteger.printTypeWithReified()
    }
}