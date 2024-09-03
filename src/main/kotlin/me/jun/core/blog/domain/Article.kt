package me.jun.core.blog.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@Entity
open class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var articleId: Long?,

    @Embedded
    open var articleInfo: ArticleInfo?,

    @Column(nullable = false)
    open var writerId: Long,

    @Column(nullable = false, updatable = false)
    @CreatedDate
    open var createdAt: Instant?,

    @Column(nullable = false)
    @LastModifiedDate
    open var updatedAt: Instant?
) {

    fun updateArticleInfo(newTitle: String, newContent: String): Article {
        articleInfo = articleInfo?.updateTitle(newTitle)
            ?.updateContent(newContent)

        return this;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Article) return false

        if (articleId != other.articleId) return false

        return true
    }

    override fun hashCode(): Int {
        return articleId?.hashCode() ?: 0
    }
}
