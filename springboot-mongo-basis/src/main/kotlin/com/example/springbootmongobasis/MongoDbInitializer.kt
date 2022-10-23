package com.example.springbootmongobasis

import com.example.springbootmongobasis.domain.user.User
import com.example.springbootmongobasis.domain.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.data.mongodb.core.BulkOperations.BulkMode
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MongoDbInitializer(
    private val mongoTemplate: MongoTemplate,
    private val userRepository: UserRepository
) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        this.bulkInsertUsers()
    }

    private fun bulkInsertUsers() {
        val users = (1..100).map { sequence ->
            User(
                userUniqueId = UUID.randomUUID().toString(),
                name = "홍길동-${UUID.randomUUID().toString().substring(1, 5)}"
            )
        }

        val bulkOperation = mongoTemplate.bulkOps(BulkMode.UNORDERED, User::class.java)
        bulkOperation.insert(users)
        val bulkOperationResult = bulkOperation.execute()

        log.debug("insertedCount : ${bulkOperationResult.insertedCount}")

        val currentUser = User(
            userUniqueId = UUID.randomUUID().toString(),
            name = "홍길동-${UUID.randomUUID().toString().substring(1, 5)}"
        )

        userRepository.save(currentUser)
        userRepository.findById(currentUser.id)
    }
}
