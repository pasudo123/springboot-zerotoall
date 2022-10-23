package com.example.springbootmongobasis.util

import java.util.UUID

object UuidUtils

fun UUID.toLineString(): String = this.toString().replace("-", "").substring(0, 10)
