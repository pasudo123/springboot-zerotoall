package com.example.springbootbasis.util

import com.example.springbootbasis.constant.Constant.COMMA
import com.example.springbootbasis.constant.Constant.SERVICES
import org.springframework.core.env.Environment

object EnvUtil {

    fun Environment.getFirstElementByProperty(): String {
        return this.getProperty(SERVICES)?.split(COMMA)?.first() ?: "empty"
    }
}