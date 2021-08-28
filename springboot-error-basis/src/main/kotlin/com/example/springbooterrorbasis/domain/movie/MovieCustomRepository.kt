package com.example.springbooterrorbasis.domain.movie

import com.example.springbooterrorbasis.domain.actor.QActor.actor
import com.example.springbooterrorbasis.domain.movie.QMovie.movie
import com.example.springbooterrorbasis.domain.song.QSong.song
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional(readOnly = true)
class MovieCustomRepository: QuerydslRepositorySupport(Movie::class.java) {

    fun findOneWithSongsAndActorsById(id: Long): Optional<Movie> {
        return Optional.ofNullable(
            from(movie)
                .innerJoin(movie.songs, song).fetchJoin()
                .innerJoin(movie.actors, actor).fetchJoin()
                .where(
                    movie.id.eq(id)
                )
                .fetchOne()
        )
    }

    fun findAllWithSongsAndActors(): List<Movie> {
        return from(movie)
            .innerJoin(movie.songs, song).fetchJoin()
            .innerJoin(movie.actors, actor).fetchJoin()
            .fetch()
    }
}