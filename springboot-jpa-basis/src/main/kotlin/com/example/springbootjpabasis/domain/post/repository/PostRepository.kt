package com.example.springbootjpabasis.domain.post.repository

import com.example.springbootjpabasis.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.history.RevisionRepository
import org.springframework.stereotype.Repository

/**
 * <{Entity}, Long, Long>
 *     첫번째 : entity
 *     두번째 : entity id 속성 값
 *     세번째 : revision number
 */
@Repository
interface PostRepository : JpaRepository<Post, Long>, RevisionRepository<Post, Long, Int>