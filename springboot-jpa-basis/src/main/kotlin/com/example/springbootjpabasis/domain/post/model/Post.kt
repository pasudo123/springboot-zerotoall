package com.example.springbootjpabasis.domain.post.model

import com.example.springbootjpabasis.domain.BaseEntity
import com.example.springbootjpabasis.domain.post.api.dto.PostCreateDto
import com.example.springbootjpabasis.domain.postdetail.model.PostDetail
import org.hibernate.envers.Audited
import org.hibernate.envers.NotAudited
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table

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
