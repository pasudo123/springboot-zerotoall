package com.example.springbootbasis.service

import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component("myHolder")
class MyHolder {

    val store: MutableMap<String, String> = mutableMapOf()

    @PostConstruct
    fun init() {
        this.store.clear()
    }

    fun addKeyAndValue(key: String, value: String) {
        this.store[key] = value
    }
}