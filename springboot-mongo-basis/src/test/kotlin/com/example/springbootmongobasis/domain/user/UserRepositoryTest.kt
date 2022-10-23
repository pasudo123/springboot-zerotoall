package com.example.springbootmongobasis.domain.user

import com.example.springbootmongobasis.MongoRepositorySupport
import io.kotest.assertions.asClue
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.time.LocalDateTime
import java.util.UUID

@MongoRepositorySupport
@DisplayName("UserRepository 는")
class UserRepositoryTest(
    private val userRepository: UserRepository
) {

    @BeforeEach
    fun init() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("user 를 저장 및 조회한다.")
    fun saveAndFindTest() {
        val user = User(UUID.randomUUID().toString(), "홍길동1")

        userRepository.save(user)

        userRepository.findById(user.id).get().name shouldBe "홍길동1"
    }
    
    @Test
    @DisplayName("특정 이름으로 시작하는 유저를 페이징 조회한다.")
    fun findAllByNamesStartWithTest() {
    
        // given
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동10"))
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동11"))
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동12"))
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동13"))
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동31"))
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동32"))
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동33"))
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동34"))
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동35"))
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동36"))
        
        // then
        userRepository.findAll().size shouldBe 10
        userRepository.findAllByNameStartsWith("홍길동3", Pageable.unpaged()).size shouldBe 6

        var pages = userRepository.findAllByNameStartsWith("홍길동3", PageRequest.of(0, 2, Sort.Direction.ASC, "id"))
        pages.asClue { current ->
            current.size shouldBe 2
            current.totalPages shouldBe 3
            current.totalElements shouldBe 6
            current.content.map { it.name } shouldContain "홍길동31"
            current.content.map { it.name } shouldContain "홍길동32"
        }

        pages = userRepository.findAllByNameStartsWith("홍길동3", PageRequest.of(1, 3, Sort.Direction.ASC, "id"))
        pages.asClue { current ->
            current.size shouldBe 3
            current.totalPages shouldBe 2
            current.totalElements shouldBe 6
            current.content.map { it.name } shouldContain "홍길동34"
            current.content.map { it.name } shouldContain "홍길동35"
            current.content.map { it.name } shouldContain "홍길동36"
        }
    }
    
    @Test
    @DisplayName("유저 생성날짜 기준으로 페이징 조회한다.")
    fun findAllByCreatedAtBeforeTest() {

        // given
        val now = LocalDateTime.of(2022, 10, 10, 10, 0, 0)

        userRepository.save(User(UUID.randomUUID().toString(), "홍길동10").apply { this.createdAt = now.minusDays(5) })
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동11").apply { this.createdAt = now.minusDays(4) })
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동12").apply { this.createdAt = now.minusDays(3) })
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동13").apply { this.createdAt = now.minusDays(2) })
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동31").apply { this.createdAt = now.minusDays(1) })
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동32").apply { this.createdAt = now.minusDays(-1) })
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동33").apply { this.createdAt = now.minusDays(-2) })

        userRepository.save(User(UUID.randomUUID().toString(), "홍길동34").apply { this.createdAt = now.minusDays(-3) })
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동35").apply { this.createdAt = now.minusDays(-4) })
        userRepository.save(User(UUID.randomUUID().toString(), "홍길동36").apply { this.createdAt = now.minusDays(-5) })

        // when & then
        var pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "id")
        var pages = userRepository.findAllByCreatedAtBefore(now, pageable)
        pages.asClue { current ->
            current.size shouldBe 2
            current.totalPages shouldBe 3
            current.totalElements shouldBe 5
            current.content.map { it.name } shouldContainAll listOf("홍길동10", "홍길동11")
        }

        pageable = PageRequest.of(0, 4, Sort.Direction.ASC, "id")
        pages = userRepository.findAllByCreatedAtBefore(now.plusDays(3), pageable)
        pages.asClue { current ->
            current.size shouldBe 4
            current.totalPages shouldBe 2
            current.totalElements shouldBe 7
            current.content.map { it.name } shouldContainAll listOf("홍길동10", "홍길동11", "홍길동12", "홍길동13")
        }
    }
}
