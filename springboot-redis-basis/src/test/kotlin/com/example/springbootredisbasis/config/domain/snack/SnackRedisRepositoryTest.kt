package com.example.springbootredisbasis.config.domain.snack

import com.example.springbootredisbasis.CustomRedisContainer.Companion.REDIS_CONTAINER
import com.example.springbootredisbasis.RedisRepositorySupport
import com.example.springbootredisbasis.repository.RedisDefaultRepository
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import mu.KotlinLogging
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

@DisplayName("SnackRedisRepository 는")
@RedisRepositorySupport
internal class SnackRedisRepositoryTest(
    private val snackRedisRepository: SnackRedisRepository
) {

    private val logger = KotlinLogging.logger {}
    private val atomicId = AtomicLong(0)

    @BeforeEach
    fun initTest() {
        REDIS_CONTAINER shouldNotBe null
        logger.info { "redis host : ${REDIS_CONTAINER.host}" }
        logger.info { "redis firstMappedPort : ${REDIS_CONTAINER.firstMappedPort}" }
        logger.info { "redis exposedPorts : ${REDIS_CONTAINER.exposedPorts}" }
        logger.info { "redis portBindings : ${REDIS_CONTAINER.portBindings}" }
    }

    @Test
    @DisplayName("저장한다.")
    fun saveTest() {

        // given
        val snack = Snack(atomicId.getAndIncrement(), "새우깡", 500)
        val redisTTL = RedisDefaultRepository.RedisTTL(5, TimeUnit.SECONDS)

        // when
        snackRedisRepository.save(snack, redisTTL) shouldBe true
    }

    @Test
    @DisplayName("저장하고 조회한다.")
    fun saveAndFindTest() {

        // given
        val snack = Snack(atomicId.getAndIncrement(), "포카칩", 1300)
        val redisTTL = RedisDefaultRepository.RedisTTL(10, TimeUnit.SECONDS)

        // when
        snackRedisRepository.save(snack, redisTTL) shouldBe true

        // then
        val actual = snackRedisRepository.findOne(snack.id)
        actual?.asClue {
            it.id shouldBe snack.id
            it.name shouldBe snack.name
            it.price shouldBe snack.price
        }
    }
}