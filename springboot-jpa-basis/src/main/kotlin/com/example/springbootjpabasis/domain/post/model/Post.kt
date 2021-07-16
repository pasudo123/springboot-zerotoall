package com.example.springbootjpabasis.domain.post.model

import com.example.springbootjpabasis.domain.BaseEntity
import com.example.springbootjpabasis.domain.post.api.dto.PostCreateDto
import org.hibernate.envers.Audited
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Audited
@Entity
@Table(name = "post")
class Post(
    @Column(name = "contents", columnDefinition = "TEXT", nullable = true)
    val contents: String
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun from(postCreateDto: PostCreateDto): Post {
            return postCreateDto.run {
                Post(this.contents)
            }
        }
    }
}