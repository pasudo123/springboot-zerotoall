package com.example.springbooterrorbasis.domain.song

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class SongCustomRepository: QuerydslRepositorySupport(Song::class.java) {
}