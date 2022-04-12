package rsync

import com.github.fracpete.processoutput4j.core.StreamingProcessOutputType
import com.github.fracpete.processoutput4j.core.StreamingProcessOwner

class CustomOutput : StreamingProcessOwner {

    override fun getOutputType(): StreamingProcessOutputType {
        return StreamingProcessOutputType.BOTH
    }

    override fun processOutput(line: String?, stdout: Boolean) {
        if (stdout) {
            println("[OUT] : $line")
        } else {
            println("[ERR] : $line")
        }
    }
}