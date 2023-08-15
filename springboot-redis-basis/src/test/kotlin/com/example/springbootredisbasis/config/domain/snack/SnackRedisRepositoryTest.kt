package com.example.springbootredisbasis.config.domain.snack

import com.example.springbootredisbasis.CustomRedisContainer.Companion.REDIS_CONTAINER
import com.example.springbootredisbasis.RedisRepositorySupport
import com.example.springbootredisbasis.config.toJson
import com.example.springbootredisbasis.domain.snack.Snack
import com.example.springbootredisbasis.domain.snack.SnackRedisRepository
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
        snackRedisRepository.flush()
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

    @Test
    @DisplayName("저장하고 전체 키를 scan 을 통해 조회한다.")
    fun saveAndScanTest() {

        // given
        (1..123).forEach {
            val snack = Snack(atomicId.getAndIncrement(), "포카칩-$it", 1300)
            val redisTTL = RedisDefaultRepository.RedisTTL(30, TimeUnit.SECONDS)
            snackRedisRepository.save(snack, redisTTL)
        }

        // when
        val keys = snackRedisRepository.findKeys()

        // then
        logger.info { keys.toJson() }
        keys.size shouldBe 123
    }
}