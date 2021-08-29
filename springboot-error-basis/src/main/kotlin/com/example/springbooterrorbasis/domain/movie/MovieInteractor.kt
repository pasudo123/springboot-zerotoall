package com.example.springbooterrorbasis.domain.movie

import com.example.springbooterrorbasis.domain.actor.ActorRepository
import com.example.springbooterrorbasis.domain.song.SongRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
@Transactional
class MovieInteractor(
    val actorRepository: ActorRepository,
    val songRepository: SongRepository,
    val movieRepository: MovieRepository,
    val movieCustomRepository: MovieCustomRepository
) {

    fun saveMovie(
        request: MovieResources.Request
    ): Movie {
        return movieRepository.save(Movie(request.title))
    }

    fun addActor(
        actorId: Long,
        movieId: Long
    ): Movie {
        val actor = actorRepository.findByIdOrNull(actorId) ?: throw EntityNotFoundException("액터를 찾을 수 없습니다.")
        val movie = movieCustomRepository.findOneWithActors(movieId).orElseThrow { throw EntityNotFoundException("영화를 찾을 수 없습니다.") }
        movie.addActor(actor)
        return movie
    }

    fun addSong(
        songId: Long,
        movieId: Long
    ): Movie {
        val song = songRepository.findByIdOrNull(songId) ?: throw EntityNotFoundException("노래를 찾을 수 없습니다.")
        val movie = movieCustomRepository.findOneWithSongs(movieId).orElseThrow { throw EntityNotFoundException("영화를 찾을 수 없습니다.") }
        movie.addSong(song)
        return movie
    }

    fun getMovieByIdExpectedThrow(id: Long): Movie {
        return movieCustomRepository.findOneWithSongsAndActorsById(id)
            .orElseThrow { throw EntityNotFoundException("영화를 찾을 수 없습니다.") }
    }

    fun getMovieById(id: Long): Movie {
        val movie = movieCustomRepository.findOneWithSongs(id).orElseThrow { throw EntityNotFoundException("영화를 찾을 수 없습니다.") }
        return movieCustomRepository.findOneWithActors(movie.id!!).orElseThrow { throw EntityNotFoundException("영화를 찾을 수 없습니다.") }
    }

    fun getAll(): List<Movie> {
        return movieCustomRepository.findAllWithSongsAndActors()
    }
}