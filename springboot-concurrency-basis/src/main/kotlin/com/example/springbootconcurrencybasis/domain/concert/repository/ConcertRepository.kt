package com.example.springbootconcurrencybasis.domain.concert.repository

import com.example.springbootconcurrencybasis.domain.concert.model.Concert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConcertRepository: JpaRepository<Concert, Long>