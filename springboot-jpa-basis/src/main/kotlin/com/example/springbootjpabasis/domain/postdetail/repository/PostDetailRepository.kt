package com.example.springbootjpabasis.domain.postdetail.repository

import com.example.springbootjpabasis.domain.postdetail.model.PostDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostDetailRepository : JpaRepository<PostDetail, Long> {

    fun findByPostId(postId: Long): PostDetail?
}
