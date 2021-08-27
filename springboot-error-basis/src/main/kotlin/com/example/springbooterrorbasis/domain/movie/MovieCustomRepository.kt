package com.example.springbooterrorbasis.domain.movie

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class MovieCustomRepository: QuerydslRepositorySupport(Movie::class.java) {
}