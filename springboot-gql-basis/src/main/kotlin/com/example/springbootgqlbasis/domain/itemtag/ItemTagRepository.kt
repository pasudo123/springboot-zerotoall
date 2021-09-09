package com.example.springbootgqlbasis.domain.itemtag

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemTagRepository : JpaRepository<ItemTag, Long> {
}