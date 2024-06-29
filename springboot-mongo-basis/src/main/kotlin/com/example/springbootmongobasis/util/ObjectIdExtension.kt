package com.example.springbootmongobasis.util

import org.bson.types.ObjectId

object ObjectIdExtension

fun String.toObjectIdOrNull(): ObjectId? {
    return try {
        return ObjectId(this)
    } catch (exception: IllegalArgumentException) {
        null
    }
}