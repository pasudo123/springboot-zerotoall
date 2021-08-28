package com.example.springbooterrorbasis.domain.song

import com.example.springbooterrorbasis.domain.BaseEntity
import com.example.springbooterrorbasis.domain.movie.Movie
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "song")
class Song(
    @Column(name = "name", columnDefinition = "VARCHAR(50) not null")
    val name: String,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @ManyToOne(targetEntity = Movie::class, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "movie_id")
    var movie: Movie? = null

    fun settingMovie(movie: Movie) {
        if (this.movie != null) {
            return
        }

        this.movie = movie
        this.movie!!.updateSong(this)
    }
}