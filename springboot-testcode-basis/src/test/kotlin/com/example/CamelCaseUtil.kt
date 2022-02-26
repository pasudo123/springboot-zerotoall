package com.example

import java.util.*

object CamelCaseUtil {

    private const val regex = "([a-z])([A-Z])";
    private const val replacement = "$1_$2";

    fun String.toUnderscore(): String {
        return this.replace(regex, replacement).lowercase(Locale.getDefault());
    }
}