package com.example.springboottestcodebasis.domain.post.repository

import com.example.springboottestcodebasis.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>