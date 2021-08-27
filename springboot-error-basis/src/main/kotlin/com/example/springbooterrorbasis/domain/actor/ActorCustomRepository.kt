package com.example.springbooterrorbasis.domain.actor

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class ActorCustomRepository: QuerydslRepositorySupport(Actor::class.java) {

}