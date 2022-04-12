package rsync

import com.github.fracpete.processoutput4j.output.ConsoleOutputProcessOutput
import com.github.fracpete.processoutput4j.output.StreamingProcessOutput
import com.github.fracpete.rsync4j.RSync

class Rsync4jEx {

    fun simpleRsync() {
        val rsync = RSync()
            .source("/Users/pasudo.dev/pasudo-study/rsyncdir/one/")
            .destination("/Users/pasudo.dev/pasudo-study/rsyncdir/two")
            .recursive(true)

        val output = rsync.execute()
        println("out : ${output.stdOut}")
        val exitCode = output.exitCode
        println("exit code : $exitCode")

        if (exitCode > 0) {
            println("err : ${output.stdErr}")
        }
    }

    fun simpleRsyncWithOption() {
        val sour = "/Users/pasudo.dev/pasudo-study/rsyncdir/one/"
        val dest = "/Users/pasudo.dev/pasudo-study/rsyncdir/two"

        val rsync = RSync().also {
            it.setOptions(arrayOf("-r", sour, dest))
        }

        val output = rsync.execute()
        println("out : ${output.stdOut}")
        val exitCode = output.exitCode
        println("exit code : $exitCode")

        if (exitCode > 0) {
            println("err : ${output.stdErr}")
        }
    }

    fun simpleRsyncWithConsole() {
        val sour = "/Users/pasudo.dev/pasudo-study/rsyncdir/one/"
        val dest = "/Users/pasudo.dev/pasudo-study/rsyncdir/two"

        val rsync = RSync()
            .source(sour)
            .destination(dest)
            .archive(true)
            .delete(true)

        val output = ConsoleOutputProcessOutput()
        output.monitor(rsync.builder())
    }

    fun simpleRsyncWithStreaming() {
        val sour = "/Users/pasudo.dev/pasudo-study/rsyncdir/one/"
        val dest = "/Users/pasudo.dev/pasudo-study/rsyncdir/two"

        val rsync = RSync()
            .source(sour)
            .destination(dest)
            .recursive(true)
            .verbose(true)

        val output = StreamingProcessOutput(CustomOutput())
        output.monitor(rsync.builder())
    }
}