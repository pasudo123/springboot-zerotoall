package com.example.springbootreactivebasis.presentation.person

import com.example.springbootreactivebasis.domain.person.Person
import com.example.springbootreactivebasis.domain.person.PersonRepository
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime

@RestController
@RequestMapping("persons")
class PersonController(
    private val personRepository: PersonRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping
    suspend fun addPerson() {
        personRepository.save(Person.random())
    }

    @GetMapping
    suspend fun findAll(): List<Person> {
        if (LocalTime.now().second % 10 == 0) {
            log.info("findAll Persons")
        }
        return personRepository.findAll()
    }

    @GetMapping("block")
    fun findAllWithBlock(): List<Person> {
        if (LocalTime.now().second % 10 == 0) {
            log.info("findAll Persons With Block")
        }
        return personRepository.findAllWithBlock()
    }
}
