package me.jun.core.blog.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@EntityListeners(AuditingEntityListener::class)
@Entity
open class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var articleId: Long?,

    @Column(nullable = false)
    open var categoryId: Long?,

    @Embedded
    open var articleInfo: ArticleInfo?,

    @Embedded
    open var writer: Writer,

    @Column(nullable = false, updatable = false)
    @CreatedDate
    open var createdAt: Instant?,

    @Column(nullable = false)
    @LastModifiedDate
    open var updatedAt: Instant?
) {

    fun updateArticleInfo(writerId: Long, newTitle: String, newContent: String): Article {
        writer.validate(writerId)
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

    override fun toString(): String {
        return "Article(articleId=$articleId, categoryId=$categoryId, articleInfo=$articleInfo, writer=$writer, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}
