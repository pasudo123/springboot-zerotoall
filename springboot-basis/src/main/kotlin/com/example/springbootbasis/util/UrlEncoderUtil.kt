package com.example.springbootbasis.util

import java.net.URLDecoder
import java.net.URLEncoder

object UrlEncoderUtil

fun String.urlEncode(): String = URLEncoder.encode(this, "UTF-8")

fun String.urlDecode(): String = URLDecoder.decode(this, "UTF-8")
