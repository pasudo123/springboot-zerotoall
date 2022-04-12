package rsync

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ProcessBuilder")
internal class ProcessBuilderExTest {

    @Test
    @DisplayName("자바버전 획득")
    fun getJavaVersion() {

        // given
        val process = ProcessBuilder("java", "-version").apply {
            this.redirectOutput(ProcessBuilder.Redirect.INHERIT)
            this.redirectError(ProcessBuilder.Redirect.INHERIT)
        }.start()

        // when


        // then
    }
}