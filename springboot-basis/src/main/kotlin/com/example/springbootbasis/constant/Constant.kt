package com.example.springbootbasis.constant

object Constant {

    const val SERVICES = "services"
    const val COMMA = ","

    object Service {
        fun createBeanName(prefix: String): String {
            return "${prefix}KService"
        }
    }
}
