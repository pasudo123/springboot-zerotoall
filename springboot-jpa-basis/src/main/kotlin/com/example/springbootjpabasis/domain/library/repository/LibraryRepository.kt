package com.example.springbootjpabasis.domain.library.repository

import com.example.springbootjpabasis.domain.library.model.Library
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.history.RevisionRepository

interface LibraryRepository : JpaRepository<Library, Long>, RevisionRepository<Library, Long, Int>
