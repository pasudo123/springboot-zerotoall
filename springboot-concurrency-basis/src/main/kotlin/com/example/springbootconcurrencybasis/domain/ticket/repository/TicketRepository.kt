package com.example.springbootconcurrencybasis.domain.ticket.repository

import com.example.springbootconcurrencybasis.domain.ticket.model.Ticket
import org.springframework.data.jpa.repository.JpaRepository

interface TicketRepository : JpaRepository<Ticket, Long>