package com.example.springbootjpabasis.domain.library.repository

import com.example.springbootjpabasis.domain.library.model.Library
import org.springframework.data.jpa.repository.JpaRepository

interface LibraryRepository : JpaRepository<Library, Long> {
}