package com.example.springbootbasis.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component("myHolder")
class MyHolder {

    val names: MutableList<String> = mutableListOf()
    val store: MutableMap<String, String> = mutableMapOf()

    private val log = LoggerFactory.getLogger(javaClass)

    @PostConstruct
    fun init() {
        this.names.clear()
        this.store.clear()
    }

    fun addKeyAndValue(key: String, value: String) {
        this.names.add(value)
        this.store[key] = value
    }

    fun getNameByType(param: Any): String {
        return "empty"
    }
}