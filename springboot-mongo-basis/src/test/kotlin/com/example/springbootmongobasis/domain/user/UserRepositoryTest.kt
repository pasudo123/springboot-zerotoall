package com.example.springbootmongobasis.domain.user

import com.example.springbootmongobasis.MongoRepositorySupport
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.UUID

@MongoRepositorySupport
@DisplayName("UserRepository 는")
class UserRepositoryTest(
    private val userRepository: UserRepository
) {

    @Test
    @DisplayName("user 를 저장한다.")
    fun saveTest() {
        val user = User(UUID.randomUUID().toString(), "홍길동1")

        userRepository.save(user)
        userRepository.findById(user.id)
    }
}
