package com.example.springbooterrorbasis.domain.actor

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActorRepository : JpaRepository<Actor, Long>
