package com.example.springbootjpabasis.domain.post.model

import com.example.springbootjpabasis.domain.BaseEntity
import com.example.springbootjpabasis.domain.post.api.dto.PostCreateDto
import com.example.springbootjpabasis.domain.postdetail.model.PostDetail
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.envers.Audited
import org.hibernate.envers.NotAudited

@Audited
@Entity
@Table(name = "post")
class Post(
    @Column(name = "contents", columnDefinition = "TEXT", nullable = true)
    var contents: String? = null
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @NotAudited
    @OneToOne(mappedBy = "post", cascade = [CascadeType.ALL])
    var postDetail: PostDetail? = null

    fun setBy(postDetail: PostDetail) {
        this.postDetail = postDetail
        postDetail.setBy(this)
    }

    fun update(contents: String) {
        this.contents = contents
    }

    companion object {
        fun from(postCreateDto: PostCreateDto): Post {
            return postCreateDto.run {
                Post(this.contents)
            }
        }
    }
}
