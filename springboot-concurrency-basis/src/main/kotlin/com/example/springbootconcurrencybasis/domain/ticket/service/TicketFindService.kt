package com.example.springbootconcurrencybasis.domain.ticket.service

import com.example.springbootconcurrencybasis.domain.ticket.repository.TicketRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TicketFindService(
    private val ticketRepository: TicketRepository
) {
}