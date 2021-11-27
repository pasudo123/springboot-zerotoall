package com.example.springboottestcodebasis.domain.comment.repository

import com.example.springboottestcodebasis.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long>