package com.example.springboottestcodebasis.archunit.poc.domain.card

import com.example.springboottestcodebasis.archunit.poc.domain.owner.OwnerDomainService
import org.springframework.stereotype.Service

@Service
class CardDomainService(
    private val ownerDomainService: OwnerDomainService
) {

    fun pay() {

    }
}