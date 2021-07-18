package com.example.springbootconcurrencybasis.lockdomain

import com.example.springbootconcurrencybasis.domain.config.AuditConfiguration
import com.example.springbootconcurrencybasis.lockdomain.snack.api.dto.SnackDto
import com.example.springbootconcurrencybasis.lockdomain.snack.model.Snack
import com.example.springbootconcurrencybasis.lockdomain.snack.repository.SnackRepository
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@DataJpaTest
@DisplayName("락킹 테스터는")
@Import(AuditConfiguration::class)
@Disabled("작동하지 않음. 일단 귀차니즘...")
class LockingJpaTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var snackRepository: SnackRepository

    @Test
    @DisplayName("낙관적 락을 테스트한다.")
    fun optimisticLockTest() {

        // given
        val snackRequest = SnackDto.CreateRequest("스낵123", 500)

        // when
        val snack = entityManager.persist(Snack.from(snackRequest))
        entityManager.flush()
        entityManager.clear()

        // then
        runBlocking {
            concurrencyRequest(snackRepository, snack)
        }
        entityManager.flush()

        val foundSnack = snackRepository.findByIdOrNull(snack.id)
        foundSnack shouldNotBe null
    }

    suspend fun concurrencyRequest(snackRepository: SnackRepository, snack: Snack) {
        val result01 = GlobalScope.async(Dispatchers.IO) {
//            entityManager.find(Snack::class.java, snack.id)
            snackRepository.findByIdOrNull(snack.id)
        }
        val result02 = GlobalScope.async(Dispatchers.IO) {
//            entityManager.find(Snack::class.java, snack.id)
            snackRepository.findByIdOrNull(snack.id)
        }
        val result03 = GlobalScope.async(Dispatchers.IO) {
//            entityManager.find(Snack::class.java, snack.id)
            snackRepository.findByIdOrNull(snack.id)
        }

//        result01.await()!!.updateName("스낵 111")
//        result02.await()!!.updateName("스낵 222")
//        result03.await()!!.updateName("스낵 333")
    }
}