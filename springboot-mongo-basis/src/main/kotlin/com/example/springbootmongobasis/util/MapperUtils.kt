package com.example.springbootmongobasis.util

import com.example.springbootmongobasis.util.MapperUtils.mapper
import com.fasterxml.jackson.databind.ObjectMapper

object MapperUtils {
    lateinit var mapper: ObjectMapper
}

fun Any.toJsonString(): String = mapper.writeValueAsString(this)
