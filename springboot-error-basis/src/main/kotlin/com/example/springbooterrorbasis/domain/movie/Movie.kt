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
    title: String
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(name = "title", columnDefinition = "VARCHAR(50) not null")
    var title: String = title
        protected set

    @OneToMany(targetEntity = Actor::class, mappedBy = "movie", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var actors: MutableList<Actor> = mutableListOf()

    @OneToMany(targetEntity = Song::class, mappedBy = "movie", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var songs: MutableList<Song> = mutableListOf()

    fun updateTitle(title: String) {
        this.title = title
    }

    fun addActor(actor: Actor) {
        val actorIds = actors.map { currentActor -> currentActor.id }
        if (actorIds.contains(actor.id).not()) {
            actors.add(actor)
            actor.settingMovie(this)
        }
    }

    fun addSong(song: Song) {
        val songIds = songs.map { currentSong -> currentSong.id }
        if (songIds.contains(song.id).not()) {
            songs.add(song)
            song.settingMovie(this)
        }
    }
}