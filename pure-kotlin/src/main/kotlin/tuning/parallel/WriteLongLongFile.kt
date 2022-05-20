package tuning.parallel

import tuning.parallel.Converter.filePath
import java.io.File
import kotlin.system.measureTimeMillis

class WriteLongLongFile

fun main() {

    val file = File(filePath)
    val elapsed = measureTimeMillis {
        file.bufferedWriter().use { out ->
            (1..5_000_000).forEach{ index ->
                out.write("$index|${index}person")
                out.newLine()
            }
        }
    }

    println("elapsed : $elapsed")
}