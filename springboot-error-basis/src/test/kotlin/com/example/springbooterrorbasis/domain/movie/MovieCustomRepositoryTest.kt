package com.example.springbooterrorbasis.domain.movie

import com.example.springbooterrorbasis.RepositoryTestSupport
import com.example.springbooterrorbasis.domain.song.Song
import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

/**
 * https://stackoverflow.com/questions/48347088/spring-test-with-datajpatest-cant-autowire-class-with-repository-but-with-in
 * https://stackoverflow.com/questions/41081589/datajpatest-needing-a-class-outside-the-test/41084739#41084739
 */
@DisplayName("MovieCustomRepository 는")
@Import(MovieCustomRepository::class)
class MovieCustomRepositoryTest : RepositoryTestSupport() {

    @Autowired
    private lateinit var movieCustomRepository: MovieCustomRepository

    private val logger = KotlinLogging.logger {}

    @Test
    @DisplayName("innerJoin 쿼리를 확인한다.")
    fun innerJoinTest() {

        // given
        val movie = Movie("스파이더맨")
        testEntityManager.persistAndFlush(movie)
        val song1 = Song("거미여왕의 노래")
        val song2 = Song("거미왕자의 노래")
        val song3 = Song("거미의 노래")
        val song4 = Song("스파이더맨 노래")
        movie.addSong(testEntityManager.persistAndFlush(song1))
        movie.addSong(testEntityManager.persistAndFlush(song2))
        movie.addSong(testEntityManager.persistAndFlush(song3))
        movie.addSong(testEntityManager.persistAndFlush(song4))
        testEntityManager.clear()

        // when
        logger.info { "============================================" }
        val foundMovie = movieCustomRepository.findOneById(movie.id!!).get()

        // then
        foundMovie.id!! shouldBe movie.id!!

        /****
        select
        distinct movie0_.id as id1_1_0_,
        songs1_.id as id1_2_1_,
        movie0_.created_at as created_2_1_0_,
        movie0_.modified_at as modified3_1_0_,
        movie0_.title as title4_1_0_,
        songs1_.created_at as created_2_2_1_,
        songs1_.modified_at as modified3_2_1_,
        songs1_.movie_id as movie_id5_2_1_,
        songs1_.name as name4_2_1_,
        songs1_.movie_id as movie_id5_2_0__,
        songs1_.id as id1_2_0__
        from
        movie movie0_
        inner join
        song songs1_
        on movie0_.id=songs1_.movie_id
        where
        movie0_.id=?
         */
    }

    @Test
    @DisplayName("join 쿼리를 확인한다.")
    fun joinTest() {
        /**
        select
            distinct movie0_.id as id1_1_0_,
            songs1_.id as id1_2_1_,
            movie0_.created_at as created_2_1_0_,
            movie0_.modified_at as modified3_1_0_,
            movie0_.title as title4_1_0_,
            songs1_.created_at as created_2_2_1_,
            songs1_.modified_at as modified3_2_1_,
            songs1_.movie_id as movie_id5_2_1_,
            songs1_.name as name4_2_1_,
            songs1_.movie_id as movie_id5_2_0__,
            songs1_.id as id1_2_0__
        from
        movie movie0_
                inner join
                song songs1_
                on movie0_.id=songs1_.movie_id
                where
        movie0_.id=?
        **/
    }

    fun leftJoinTest() {
        /**

        select
            distinct movie0_.id as id1_1_0_,
            songs1_.id as id1_2_1_,
            movie0_.created_at as created_2_1_0_,
            movie0_.modified_at as modified3_1_0_,
            movie0_.title as title4_1_0_,
            songs1_.created_at as created_2_2_1_,
            songs1_.modified_at as modified3_2_1_,
            songs1_.movie_id as movie_id5_2_1_,
            songs1_.name as name4_2_1_,
            songs1_.movie_id as movie_id5_2_0__,
            songs1_.id as id1_2_0__
        from
        movie movie0_
                left outer join
        song songs1_
                on movie0_.id=songs1_.movie_id
                where
        movie0_.id=?

        **/
    }
}