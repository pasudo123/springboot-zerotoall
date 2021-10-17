package com.example.springboottestcodebasis.dirtycontext

import org.springframework.stereotype.Component

@Component
class UserCache {

    private val users = mutableSetOf<String>()

    fun addUser(name: String): Boolean {
        return users.add(name)
    }

    fun printUsers(): Unit {
        println(users)
    }

    fun getUsers(): Set<String> {
        return users
    }
}