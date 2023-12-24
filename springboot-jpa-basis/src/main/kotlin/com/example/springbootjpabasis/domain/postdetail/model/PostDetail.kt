package com.example.springbootjpabasis.domain.postdetail.model

import com.example.springbootjpabasis.domain.BaseEntity
import com.example.springbootjpabasis.domain.post.model.Post
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "post_detail")
class PostDetail(
    paramTags: List<String> = emptyList()
) : BaseEntity() {

    /**
     * 필드만 설정되어있고, 실제 테이블에 컬럼 미존재.
     */
    @Id
    var id: Long? = null

    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    var post: Post? = null

    @Column(name = "tags", columnDefinition = "TEXT", nullable = true)
    private val _tags = paramTags.joinToString(",")
    val tags: List<String>
        get() = _tags.split(",")

    fun setBy(post: Post) {
        this.post = post
    }
}
