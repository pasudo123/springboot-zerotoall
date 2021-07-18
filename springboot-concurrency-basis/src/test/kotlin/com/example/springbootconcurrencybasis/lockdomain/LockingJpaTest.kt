package com.example.springbootconcurrencybasis.lockdomain

import com.example.springbootconcurrencybasis.domain.config.AuditConfiguration
import com.example.springbootconcurrencybasis.lockdomain.snack.api.dto.SnackDto
import com.example.springbootconcurrencybasis.lockdomain.snack.model.Snack
import com.example.springbootconcurrencybasis.lockdomain.snack.repository.SnackRepository
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@DisplayName("락킹 테스터는")
@Import(AuditConfiguration::class)
class LockingJpaTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var snackRepository: SnackRepository

    @Test
    @DisplayName("낙관적 락을 테스트한다.")
    fun optimisticLockTest() {

        // given
        val snackRequest = SnackDto.CreateRequest("스낵123")

        // when
        val snack = entityManager.persist(Snack.from(snackRequest))
        entityManager.flush()
        entityManager.clear()

        // then
        val foundSnack = snackRepository.findByIdOrNull(snack.id)
        foundSnack!!.name shouldBe "스낵123"
        foundSnack.updateName("스낵555")

        snackRepository.findByIdOrNull(snack.id)
        entityManager.flush()
    }
}