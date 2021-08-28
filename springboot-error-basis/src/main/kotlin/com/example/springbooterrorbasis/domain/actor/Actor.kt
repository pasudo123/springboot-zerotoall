package com.example.springbooterrorbasis.domain.actor

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
@Table(name = "actor")
class Actor(
    @Column(name = "first_name", columnDefinition = "VARCHAR(50) not null")
    val firstName: String,
    @Column(name = "last_name", columnDefinition = "VARCHAR(50) not null")
    val lastName: String
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
        this.movie!!.updateActor(this)
    }
}