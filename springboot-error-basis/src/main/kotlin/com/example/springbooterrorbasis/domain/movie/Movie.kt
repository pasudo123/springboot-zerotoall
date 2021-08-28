package com.example.springbooterrorbasis.domain.movie

import com.example.springbooterrorbasis.domain.BaseEntity
import com.example.springbooterrorbasis.domain.actor.Actor
import com.example.springbooterrorbasis.domain.song.Song
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "movie")
class Movie(
    @Column(name = "title", columnDefinition = "VARCHAR(50) not null")
    val title: String
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @OneToMany(targetEntity = Actor::class, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var actors: MutableList<Actor> = mutableListOf()

    @OneToMany(targetEntity = Song::class, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var songs: MutableList<Song> = mutableListOf()

    fun updateActor(actor: Actor) {
        actors.removeIf { currentActor -> currentActor.id == actor.id }
        actors.add(actor)
        actor.settingMovie(this)
    }

    fun updateSong(song: Song) {
        songs.removeIf { currentSong -> currentSong.id == song.id }
        songs.add(song)
        song.settingMovie(this)
    }
}