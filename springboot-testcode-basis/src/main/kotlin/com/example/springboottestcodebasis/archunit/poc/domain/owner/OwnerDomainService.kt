package com.example.springboottestcodebasis.archunit.poc.domain.owner

import com.example.springboottestcodebasis.archunit.poc.domain.card.CardDomainService
import org.springframework.stereotype.Service

@Service
class OwnerDomainService(
    private val cardDomainService: CardDomainService
) {
}