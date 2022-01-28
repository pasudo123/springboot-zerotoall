package model

import java.util.*

class Coffee(
    val name: String,
    val price: Int,
    val id: String = UUID.randomUUID().toString()
)