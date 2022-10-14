package com.example.springbootreactivebasis.presentation.person

import com.example.springbootreactivebasis.domain.person.Person
import com.example.springbootreactivebasis.domain.person.PersonRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("persons")
class PersonController(
    private val personRepository: PersonRepository
) {

    @PostMapping
    suspend fun addPerson() {
        personRepository.save(Person.random())
    }

    @GetMapping
    suspend fun findAll(): List<Person> {
        return personRepository.findAll()
    }

    @GetMapping("block")
    fun findAllWithBlock(): List<Person> {
        return personRepository.findAllWithBlock()
    }
}
