package effectivekotlin

class item35

class SqlSelectBuilder {

    fun select(vararg columns: String) {}
    fun from(table: String) {}

    fun build(): String {
        TODO("")
    }
}

fun query(initializer: SqlSelectBuilder.() -> Unit): SqlSelectBuilder {
    return SqlSelectBuilder().apply(initializer)
}

fun main() {
    val query = query {
        this.select("*")
        this.from("test")
    }.build()
}