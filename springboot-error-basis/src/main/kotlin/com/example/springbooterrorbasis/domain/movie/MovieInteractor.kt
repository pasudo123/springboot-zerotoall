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
        actorId: Long,
        songId: Long,
        request: MovieResources.Request
    ): Movie {
        val actor = actorRepository.findByIdOrNull(actorId) ?: throw EntityNotFoundException("액터를 찾을 수 없습니다.")
        val song = songRepository.findByIdOrNull(songId) ?: throw EntityNotFoundException("노래를 찾을 수 없습니다.")
        val movie = movieRepository.save(Movie(request.title))

        movie.updateSong(song)
        movie.updateActor(actor)

        return movie
    }

    fun getMovieById(id: Long): Movie {
        return movieCustomRepository.findOneWithSongsAndActorsById(id)
            .orElseThrow { throw EntityNotFoundException("노래를 찾을 수 없습니다.") }
    }
}