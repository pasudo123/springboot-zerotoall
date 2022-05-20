package tuning.parallel

object Converter {

    val filePath = "pure-kotlin/src/main/resources/files/longlong.txt"

    fun toPerson(line: String): LongLongPerson {
        val elements = line.split("|")
        return LongLongPerson(
            elements[0],
            elements[1]
        )
    }
}

