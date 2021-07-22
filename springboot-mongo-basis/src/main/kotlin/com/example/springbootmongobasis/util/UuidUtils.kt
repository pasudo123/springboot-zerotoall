package com.example.springbootmongobasis.util

import java.util.*

object UuidUtils

fun UUID.toLineString(): String = this.toString().replace("-", "").substring(0, 10)