package com.example.springboottestcodebasis.dirtycontext

import com.example.springboottestcodebasis.SpringbootTestcodeBasisApplication
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestConstructor

@DisplayName("UserCache 테스트는")
@SpringBootTest(classes = [SpringbootTestcodeBasisApplication::class])
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UserCacheTest(
    private val userCache: UserCache
) {

    @Test
    @DisplayName("(1) 유저를 추가한다.")
    @Order(1)
    fun simpleAddTest() {

        // given
        val bool = userCache.addUser("park")

        // then
        bool shouldBe true
    }

    @Test
    @DisplayName("(2) 유저를 조회한다.")
    @Order(2)
    fun simplePrintTest() {

        // given
        userCache.printUsers()
        val users = userCache.getUsers()

        // then
        users.size shouldBe 1
    }

    @Test
    @DisplayName("(3) 더티 콘텍스트를 이용하여, 초기화 후 유저를 추가한다.")
    @Order(3)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun addUserTestWithDirtyContext() {

        /**
         * 여기서 스프링 콘텍스트가 다시 올라온다.
         * BEFORE_METHOD 를 이용하여 이전에 수행한 Order(1), Order(2) 에서 수행한 항목은 초기화된다.
         */

        // given
        userCache.addUser("sung")
        userCache.printUsers()

        // when
        val users = userCache.getUsers()

        // then
        users.size shouldBe 1
    }

    @Test
    @DisplayName("(4) 유저를 새롭게 추가한다.")
    @Order(4)
    fun addNewUserTest() {

        // given
        userCache.addUser("lee")
        userCache.printUsers()

        // when
        val users = userCache.getUsers()

        // then
        users.size shouldBe 2
        users.contains("sung") shouldBe true
        users.contains("lee") shouldBe true
    }

    @Test
    @DisplayName("(5) 유저를 한번 더 추가한다.")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Order(5)
    fun addUserOneMoreTimeTest() {

        /**
         * AFTER_METHOD 를 이용하여 @Order(5) 수행 이후에 빈은 새롭게 띄어진다.
         */

        // given
        userCache.addUser("june")
        userCache.printUsers()

        // when
        val users = userCache.getUsers()

        // then
        users.size shouldBe 3
        users.contains("sung") shouldBe true
        users.contains("lee") shouldBe true
        users.contains("june") shouldBe true
    }

    @Test
    @DisplayName("(6) 유저들은 모두 초기화되었다.")
    @Order(6)
    fun userInitTest() {

        // given
        val users = userCache.getUsers()

        // then
        users.size shouldBe 0
    }
}