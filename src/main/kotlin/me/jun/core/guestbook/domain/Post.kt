package me.jun.core.guestbook.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@EntityListeners(AuditingEntityListener::class)
open class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var postId: Long?,

    @Column(nullable = false)
    open var title: String,

    @Column(nullable = false)
    open var content: String,

    @Column(nullable = false)
    open var writerId: Long,

    @Column(nullable = false)
    @CreatedDate
    open var createdAt: Instant?,

    @Column(nullable = false)
    @LastModifiedDate
    open var updatedAt: Instant?
) {

    fun updateTitle(newTitle: String): Post {
        this.title = newTitle
        return this
    }

    fun updateContent(newContent: String): Post {
        this.content = newContent
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Post) return false

        if (postId != other.postId) return false

        return true
    }

    override fun hashCode(): Int {
        return postId?.hashCode() ?: 0
    }
}
