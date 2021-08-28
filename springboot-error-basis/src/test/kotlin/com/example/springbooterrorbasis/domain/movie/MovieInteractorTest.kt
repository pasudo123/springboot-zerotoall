package com.example.springbooterrorbasis.domain.movie

import com.example.springbooterrorbasis.IntergrationSupport
import com.example.springbooterrorbasis.domain.actor.Actor
import com.example.springbooterrorbasis.domain.actor.ActorRepository
import com.example.springbooterrorbasis.domain.song.Song
import com.example.springbooterrorbasis.domain.song.SongRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import mu.KotlinLogging
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@DisplayName("MovieInteractor 는")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
internal class MovieInteractorTest : IntergrationSupport() {

    @Autowired
    private lateinit var movieInteractor: MovieInteractor

    @Autowired
    private lateinit var songRepository: SongRepository

    @Autowired
    private lateinit var actorRepository: ActorRepository

    private val logger = KotlinLogging.logger {}
    private val songIds: MutableList<Long> = mutableListOf()
    private val actorIds: MutableList<Long> = mutableListOf()

    @BeforeEach
    fun init() {
        val songNames = listOf("이런노래", "저런노래", "너랑나", "좋은날", "금요일에 만나요")
        val actorNames = listOf("홍길동", "둘리", "이순신", "강감찬", "이성계")
        songNames.forEach { name ->
            val song = songRepository.save(Song(name))
            songIds.add(song.id!!)
        }

        actorNames.forEach { name ->
            val actor = actorRepository.save(Actor(firstName = name.substring(0, 1), name.substring(1)))
            actorIds.add(actor.id!!)
        }
    }


    @Test
    @DisplayName("영화를 하나 검색하지만 한방 쿼리로 @OneToMany 관계를 여러개 들고오기 때문에 에러가 발생한다.")
    fun getMovieByIdFailTest() {

        // given
        logger.info { "===== [1] song & actor save query start" }
        val request = MovieResources.Request("내가 만든 영화")
        val movie = movieInteractor.saveMovie(request)
        actorIds.forEach { actorId ->
            movieInteractor.addActor(actorId = actorId, movieId = movie.id!!)
        }

        songIds.forEach { songId ->
            movieInteractor.addSong(songId = songId, movieId = movie.id!!)
        }

        // when
        logger.info { "===== [2] movie find query start" }
        val exception = assertThrows<InvalidDataAccessApiUsageException> {
            movieInteractor.getMovieByIdExpectedThrow(movie.id!!)
        }

        // then
        exception.message shouldContain "org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags"
    }

    @Test
    @DisplayName("영화 하나를 검색하기 위해 컬렉션 엔티티들 별도의 쿼리로 두 번 조회해서 성공시킨다.")
    fun getMovieByIdSuccessTest() {

        // given
        logger.info { "===== [1] song & actor save query start" }
        val request = MovieResources.Request("누가누가 영화")
        val movie = movieInteractor.saveMovie(request)
        actorIds.forEach { actorId ->
            movieInteractor.addActor(actorId = actorId, movieId = movie.id!!)
        }

        songIds.forEach { songId ->
            movieInteractor.addSong(songId = songId, movieId = movie.id!!)
        }

        // when
        logger.info { "===== [2] movie find query start" }
        val foundMovie = movieInteractor.getMovieById(movie.id!!)

        // then
        foundMovie.id shouldBe movie.id
        foundMovie.actors.size shouldBe 5
        foundMovie.songs.size shouldBe 5
    }
}