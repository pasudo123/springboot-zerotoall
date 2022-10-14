package com.example.springbootreactivebasis

import com.example.springbootreactivebasis.domain.person.Person
import com.example.springbootreactivebasis.domain.person.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val personRepository: PersonRepository
) : CommandLineRunner {

    override fun run(vararg args: String?): Unit = runBlocking(Dispatchers.IO) {

        val persons = (1..500000).map { _ ->
            Person.random()
        }

        persons
            .chunked(1000)
            .map { chunkPersons ->
                async {
                    personRepository.saveAll(chunkPersons)
                }
            }.awaitAll()
    }
}
