package com.example.springbooterrorbasis.domain.movie

import com.example.springbooterrorbasis.domain.actor.QActor.actor
import com.example.springbooterrorbasis.domain.movie.QMovie.movie
import com.example.springbooterrorbasis.domain.song.QSong.song
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Repository
@Transactional(readOnly = true)
class MovieCustomRepository : QuerydslRepositorySupport(Movie::class.java) {

    fun findOneWithSongs(id: Long): Optional<Movie> {
        return Optional.ofNullable(
            from(movie)
                .leftJoin(movie.songs, song).fetchJoin()
                .where(
                    movie.id.eq(id)
                )
                .distinct()
                .fetchOne()
        )
    }

    fun findOneWithActors(id: Long): Optional<Movie> {
        return Optional.ofNullable(
            from(movie)
                .leftJoin(movie.actors, actor).fetchJoin()
                .where(
                    movie.id.eq(id)
                )
                .distinct()
                .fetchOne()
        )
    }

    fun findOneWithSongsAndActorsById(id: Long): Optional<Movie> {
        return Optional.ofNullable(
            from(movie)
                .leftJoin(movie.songs, song).fetchJoin()
                .leftJoin(movie.actors, actor).fetchJoin()
                .where(
                    movie.id.eq(id)
                )
                .distinct()
                .fetchOne()
        )
    }

    fun findAllWithSongsAndActors(): List<Movie> {
        return from(movie)
            .leftJoin(movie.songs, song).fetchJoin()
            .leftJoin(movie.actors, actor).fetchJoin()
            .distinct()
            .fetch()
    }

    fun findOneById(id: Long): Optional<Movie> {
        return Optional.ofNullable(
            from(movie)
                .join(movie.songs, song).fetchJoin()
                .where(
                    movie.id.eq(id)
                )
                .distinct()
                .fetchOne()
        )
    }
}
